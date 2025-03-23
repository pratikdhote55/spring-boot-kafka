package com.practice.kafka.subscriber;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.practice.kafka.dto.User;

@Service
public class KafkaConsumer {

	@KafkaListener(topics = "my-kafka-topic-1", groupId = "my-group-id-1")
    public void consumeMessage(String message) {
        System.out.println("Consumed String message: " + message);
    }
	
	@KafkaListener(topics = "my-kafka-topic-2", groupId = "my-group-id-2")
    public void consumeMessage(User user) {
        System.out.println("Consumed Object message: " + user);
    }
	
}
