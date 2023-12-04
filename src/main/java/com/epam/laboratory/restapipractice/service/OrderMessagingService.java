package com.epam.laboratory.restapipractice.service;

import com.epam.laboratory.restapipractice.dto.OrderResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
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
    @KafkaListener(topics = TOPIC_NAME, groupId = KAFKA_CONSUMER_GROUP_ID)
    public void processOrder(String message, Acknowledgment acknowledgment) {
        log.info("Message consumed: {}", message);

        try {
            Long orderId = Long.parseLong(message);
            OrderResponseDto order = orderService.getOrder(orderId);

            if (order != null) {
                log.info("Order found. Completing order: {}", orderId);
                orderService.completeOrder(orderId);
                acknowledgment.acknowledge();
            } else {
                log.info("Order not found. Creating new order.");
                orderService.getOrder(orderId);
                acknowledgment.nack(3000);
            }
        } catch (Exception e) {
            log.error("Error processing order", e);
        }
    }
}
