package com.practice.kafka.subscriber;

import com.practice.kafka.dto.Employee;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaAvroConsumer {

    @KafkaListener(topics = "my-kafka-topic-3", groupId = "my-group-id-3")
    public void consumeMessage(ConsumerRecord<String, Employee> consumerRecord) {
        Employee employee = consumerRecord.value();
        System.out.println("Consumed Object message: " + employee + " | Key : " + consumerRecord.key());
    }

}
