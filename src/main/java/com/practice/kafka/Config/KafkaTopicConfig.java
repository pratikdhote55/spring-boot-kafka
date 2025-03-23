package com.practice.kafka.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	@Bean
	NewTopic kafkaTopic1() {
	    return TopicBuilder.name("my-kafka-topic-1").partitions(5).replicas(1).build();
	}

}
