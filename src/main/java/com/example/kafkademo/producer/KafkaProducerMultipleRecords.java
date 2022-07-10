package com.example.kafkademo.producer;

import static java.util.stream.IntStream.range;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.kafkademo.schema.DataRecordAvro;

@Service
public class KafkaProducerMultipleRecords {

  @Autowired
  private KafkaTemplate<String, DataRecordAvro> producer;
  
  @Value("${avro.topic.name2}")
  private String topic;

  
  public void produce() {
    // Produce sample data
    range(0, 5).forEach(i -> {
      final String key = "alice";
      final DataRecordAvro record = new DataRecordAvro((long) i);
      
      producer.send(topic, key, record).addCallback(
          result -> {
            final RecordMetadata m;
            if (result != null) {
              m = result.getRecordMetadata();
              System.out.println("Produced record to topic "+ m.topic());              
            }
          },
          exception ->  System.out.println("Failed to produce to kafka" + exception));
    });

    producer.flush();

    System.out.println("5 messages were produced to topic "+ topic);

  }

}
