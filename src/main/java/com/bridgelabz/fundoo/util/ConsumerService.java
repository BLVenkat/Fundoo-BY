package com.bridgelabz.fundoo.util;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.dto.UserDTO;

@Component
public class ConsumerService {

	//@KafkaListener(topics = {"test","demo"})
	@KafkaListener(topics = {"BY"})
	public void receiveData(UserDTO data) {
		System.err.println(data);
	}
}
