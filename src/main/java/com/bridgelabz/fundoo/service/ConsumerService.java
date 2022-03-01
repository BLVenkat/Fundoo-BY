package com.bridgelabz.fundoo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerService {

	//@KafkaListener(topics = {"test","demo"})
	@KafkaListener(topics = {"test"})
	public void receiveData(String data) {
		System.err.println(data);
	}
}
