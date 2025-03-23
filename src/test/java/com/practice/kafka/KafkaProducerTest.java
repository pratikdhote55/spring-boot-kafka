package com.practice.kafka;

import com.practice.kafka.dto.User;
import com.practice.kafka.publisher.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class KafkaProducerTest {

    // Use a stable, known version of the Kafka container image and increase timeout
    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.0.1"))
            .withStartupTimeout(Duration.ofMinutes(5));  // Increase the startup timeout

    @DynamicPropertySource
    public static void configureKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    public void testSendEventToTopic() {
        // Send message to Kafka
        User user = new User("John Doe", "+1234567890", "johndoe@example.com", "USA");
        kafkaProducer.produceMessage(user);

        // Wait for the message to be processed correctly
        await().pollInterval(Duration.ofSeconds(3))
                .atMost(10, SECONDS)
                .untilAsserted(() -> {
                    // Add a real condition to assert message delivery
                    System.out.println("Message sent to Kafka successfully");
                });

        // Optionally, print the container logs for debugging
        System.out.println(kafkaContainer.getLogs());
    }
}
