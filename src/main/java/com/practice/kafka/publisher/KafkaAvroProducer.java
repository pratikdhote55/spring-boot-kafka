package com.practice.kafka.publisher;

import com.practice.kafka.dto.Employee;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaAvroProducer {

    private final KafkaTemplate<String, Employee> kafkaTemplateForEmployee;

    public KafkaAvroProducer(KafkaTemplate<String, Employee> kafkaTemplateForEmployee) {
        this.kafkaTemplateForEmployee = kafkaTemplateForEmployee;
    }

    public String produceMessage(Employee employee) throws IOException {
        CompletableFuture<SendResult<String, Employee>> future = kafkaTemplateForEmployee.send("my-kafka-topic-3", UUID.randomUUID().toString(), employee);
        future.whenComplete((result,ex) -> {
           if(ex==null) {
               System.out.println("Sent Message=["+ employee +"] with offset=["+ result.getRecordMetadata().offset() +"]");
           } else {
               System.out.println("Unable to Sent Message=["+ employee +"] due to=" + ex.getMessage());
           }
        });
        return avroToJson(employee);
    }

    private String avroToJson(Employee employee) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DatumWriter<Employee> writer = new SpecificDatumWriter<>(Employee.class);
        Encoder encoder = EncoderFactory.get().jsonEncoder(Employee.getClassSchema(), outputStream);
        writer.write(employee, encoder);
        encoder.flush();
        outputStream.close();
        return outputStream.toString();
    }

}
