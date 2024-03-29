package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.dto.OrderRequestDto;
import com.epam.laboratory.restapipractice.dto.OrderResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class OrderMessagingService {

    private final OrderService orderService;
    private static final String TOPIC_NAME = "${message.topic.name}";
    private static final String KAFKA_CONSUMER_GROUP_ID = "${spring.kafka.consumer.group-id}";

    @Transactional
    @KafkaListener(topics = TOPIC_NAME, groupId = KAFKA_CONSUMER_GROUP_ID, containerFactory = "kafkaListenerContainerFactory")
    public void receiveOrderMessage(Message<?> message, Acknowledgment acknowledgment) {
        String messagePayload = message.getPayload().toString();
        String[] parts = messagePayload.split("\\|");
        if (parts.length == 2) {
            String messageCompletedOrderId = parts[0];
            String messageClientId = parts[1];

            String acceptLanguage = message.getHeaders().get("acceptLanguage").toString();
            String acceptTimezone = message.getHeaders().get("acceptTimezone").toString();

            log.info("Received order message: orderId={}, clientId={}, acceptLanguage={}, acceptTimezone={}", messageCompletedOrderId, messageClientId, acceptLanguage, acceptTimezone);

            try {
                Long orderId = Long.parseLong(messageCompletedOrderId);
                Long clientId = Long.parseLong(messageClientId);
                OrderResponseDto orderResponseDto = null;
                try {
                    orderResponseDto = orderService.getOrder(orderId, acceptLanguage, acceptTimezone);
                    log.info("Client ID value: {}", clientId);
                    orderResponseDto.setClientId(clientId);
                } catch (Exception e) {
                    log.info("Error getting order");
                }
                if (orderResponseDto == null) {
                    log.info("Order not found. Creating new order.");
                    OrderRequestDto orderRequestDto = new OrderRequestDto();
                    log.info("Client ID value: {}", clientId);
                    orderRequestDto.setClientId(clientId);
                    orderRequestDto.setDeliveryInf("1");
                    orderService.createOrder(orderRequestDto, acceptLanguage, acceptTimezone);
                } else {
                    log.info("Order found. Completing order: {}", orderId);
                    orderService.completeOrder(orderId);
                }
            } catch (Exception e) {
                log.error("Error processing order", e);
            } finally {
                acknowledgment.acknowledge();
            }
        } else {
            log.error("Invalid message format: {}", messagePayload);
        }
    }
}
