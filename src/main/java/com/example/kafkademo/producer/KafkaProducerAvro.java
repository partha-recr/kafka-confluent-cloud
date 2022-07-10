package com.example.kafkademo.producer;

import com.example.kafkademo.schema.StockHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducerAvro {

    @Value("${avro.topic.name}")
    String topicName;

    @Autowired
    private KafkaTemplate<String, StockHistory> kafkaTemplate;

    public void send(StockHistory stockHistory){
       System.out.println("MSG with Key:"+ stockHistory.getTradeId());
       ListenableFuture<SendResult<String,StockHistory>> future=  kafkaTemplate.send(topicName,String.valueOf(stockHistory.getTradeId()),stockHistory);
       future.addCallback(new ListenableFutureCallback<SendResult<String, StockHistory>>() {
           @Override
           public void onFailure(Throwable ex) {
               System.out.println("Message failed to produce");
           }

           @Override
           public void onSuccess(SendResult<String, StockHistory> result) {
               System.out.println("Avro message successfully produced");
           }
       });

    }

}
