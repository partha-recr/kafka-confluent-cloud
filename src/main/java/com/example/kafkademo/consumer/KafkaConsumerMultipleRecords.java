package com.example.kafkademo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.kafkademo.schema.DataRecordAvro;

@Service
public class KafkaConsumerMultipleRecords {

  @KafkaListener(topics = "${avro.topic.name2}", containerFactory = "kafkaListenerContainerFactory")  
  public void consume(final ConsumerRecord<Long, DataRecordAvro> consumerRecord) {
	  DataRecordAvro data = consumerRecord.value();
    System.out.println("Listener : Key :: "+consumerRecord.key() + " Value :: " + data);
  }
}
