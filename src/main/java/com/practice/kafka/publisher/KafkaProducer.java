package com.practice.kafka.publisher;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.practice.kafka.dto.User;

@Service
public class KafkaProducer {

	private final KafkaTemplate<String, String> kafkaTemplateForString;

	private final KafkaTemplate<String, Object> kafkaTemplateForJSON;

	public KafkaProducer(KafkaTemplate<String, String> kafkaTemplateForString,
			KafkaTemplate<String, Object> kafkaTemplateForJSON) {
		this.kafkaTemplateForString = kafkaTemplateForString;
		this.kafkaTemplateForJSON = kafkaTemplateForJSON;
	}

	public String produceMessage(String message) {
		kafkaTemplateForString.send("my-kafka-topic-1", 2, null, message).thenAccept(result -> System.out.println(
				"Message sent successfully: [" + message + "] to Record Metadata : " + result.getRecordMetadata()))
				.exceptionally(ex -> {
					System.err.println("Error sending message: " + ex.getMessage());
					return null;
				});
		return "Message is being sent asynchronously!";
	}

	public User produceMessage(User user) {
		kafkaTemplateForJSON.send("my-kafka-topic-2", user).thenApply(result -> {
			System.out.println(
					"Message sent successfully: [" + user + "] to Record Metadata: " + result.getRecordMetadata());
			return result;
		}).exceptionally(ex -> {
			System.err.println("Error sending message: " + ex.getMessage());
			return null;
		});

		return user;
	}

}
