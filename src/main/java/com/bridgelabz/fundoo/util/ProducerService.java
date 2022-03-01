package com.bridgelabz.fundoo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProducerService {

	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
	
	public  void sendData(String data) {
		kafkaTemplate.send("test", data);
		//kafkaTemplate.send("demo", "data from demo topic");

	}
}