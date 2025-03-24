package com.practice.kafka.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.kafka.dto.Employee;
import com.practice.kafka.dto.RetryUser;
import com.practice.kafka.publisher.KafkaAvroProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.kafka.dto.User;
import com.practice.kafka.publisher.KafkaProducer;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/kafka")
public class MessageController {

	private final KafkaProducer kafkaProducer;

	private final KafkaAvroProducer kafkaAvroProducer;

	public MessageController(KafkaProducer kafkaProducer, KafkaAvroProducer kafkaAvroProducer) {
		this.kafkaProducer = kafkaProducer;
		this.kafkaAvroProducer = kafkaAvroProducer;
	}

	@GetMapping("/publishString")
	public ResponseEntity<String> publishString(String message) {
		String sentMessage = kafkaProducer.produceMessage(message);
		return ResponseEntity.ok(sentMessage);
	}
	
	@PostMapping(value = "/publishUser")
	public ResponseEntity<User> produceUser(@RequestBody User user) {
		User sentMessage = kafkaProducer.produceMessage(user);
		return ResponseEntity.ok(sentMessage);
	}

	@PostMapping(value = "/publishEmployee")
	public ResponseEntity<String> produceEmployee(@RequestBody Employee employee) throws IOException {
		String sentMessage = kafkaAvroProducer.produceMessage(employee);
		try {
			// Convert Avro Employee to JSON String before returning
			String json = new ObjectMapper().writeValueAsString(sentMessage);
			return ResponseEntity.ok(json);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error converting to JSON: " + e.getMessage());
		}
	}

	@PostMapping(value = "/publishRetryUser")
	public ResponseEntity<RetryUser> produceUser(@RequestBody RetryUser retryUser) {
		RetryUser sentMessage = kafkaProducer.produceMessage(retryUser);
		return ResponseEntity.ok(retryUser);
	}

}
