package com.practice.kafka.subscriber;

import com.practice.kafka.dto.RetryUser;
import com.practice.kafka.dto.User;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class KafkaConsumer {

	@KafkaListener(topics = "my-kafka-topic-1", groupId = "my-group-id-1", topicPartitions = {@TopicPartition(topic="my-kafka-topic-1", partitions = {"2"})})
    public void consumeMessage(String message) {
        System.out.println("Consumed String message: " + message);
    }
	
	@KafkaListener(topics = "my-kafka-topic-2", groupId = "my-group-id-2")
    public void consumeMessage(User user) {
        System.out.println("Consumed Object message: " + user);
    }

    private static final List<String> NOT_PERMITTED_COUNTRIES = Arrays.asList("RESTRICTED", "NOT-PERMITTED");

    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 2000), include = {IllegalArgumentException.class})
    @KafkaListener(topics = "my-kafka-topic-5", groupId = "my-group-id-2")
    public void consumeMessage(RetryUser retryUser) {
        if (NOT_PERMITTED_COUNTRIES.contains(retryUser.getCountry().toUpperCase())) {
            throw new IllegalArgumentException("Country not permitted: " + retryUser.getCountry());
        }
        System.out.println("Successfully Consumed Message: " + retryUser);
    }

    @DltHandler
    public void listenDLT(RetryUser retryUser) {
        System.out.println("DLT Received: " + retryUser.getGivenName());
    }
	
}
