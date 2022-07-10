package com.example.kafkademo.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafkademo.producer.KafkaProducerAvro;
import com.example.kafkademo.producer.KafkaProducerMultipleRecords;
import com.example.kafkademo.schema.StockHistory;

@RestController
public class Controller {

	@Autowired
	private KafkaProducerAvro springAvroProducer;

	@Autowired
	private KafkaProducerMultipleRecords kafkaProducer;

	@GetMapping(value = "/sendStockHistory")
	public String sendStockHistory() {
		StockHistory stockHistory = StockHistory.newBuilder().build();
		stockHistory.setStockName("StockName");
		stockHistory.setTradeType("TradeType");
		stockHistory.setPrice(200);
		stockHistory.setAmount(100);
		stockHistory.setTradeId(new Random(1000).nextInt());
		stockHistory.setTradeMarket("TradeMarket");
		stockHistory.setTradeQuantity(15);
		springAvroProducer.send(stockHistory);
		return "Success";
	}

	@GetMapping(value = "/sendDataRecord")
	public String sendDataRecord() {
		kafkaProducer.produce();
		return "Sent Data Record";
	}
}
