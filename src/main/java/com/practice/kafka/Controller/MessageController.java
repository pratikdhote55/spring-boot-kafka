package com.practice.kafka.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.kafka.dto.User;
import com.practice.kafka.publisher.KafkaProducer;

@RestController
@RequestMapping("/api/v1/kafka")
public class MessageController {

	private KafkaProducer kafkaProducer;

	public MessageController(KafkaProducer kafkaProducer) {
		this.kafkaProducer = kafkaProducer;
	}

	@GetMapping("/publishString")
	public ResponseEntity<String> publishString(String message) {
		String sentMessage = kafkaProducer.produceMessage(message);
		return ResponseEntity.ok(sentMessage);
	}
	
	@PostMapping(value = "/publishUser")
	public ResponseEntity<User> produceUser(@RequestBody User user) {
		kafkaProducer.produceMessage(user);
		return ResponseEntity.ok(user);
	}

}
