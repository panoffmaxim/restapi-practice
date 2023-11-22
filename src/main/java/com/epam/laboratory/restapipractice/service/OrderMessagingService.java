package com.epam.laboratory.restapipractice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class OrderMessagingService {

    private final OrderService orderService;
    private static final String topicName = "${message.topic.name}";
    private static final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";

    @Transactional
    @KafkaListener(topics = topicName, groupId = kafkaConsumerGroupId)
    public void printOrder(String message) {
        log.info("Message consumed {}", message);
        orderService.completeOrder(Long.valueOf(message).longValue());
    }
}
