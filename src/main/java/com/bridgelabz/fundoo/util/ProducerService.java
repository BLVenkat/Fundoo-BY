package com.bridgelabz.fundoo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.dto.UserDTO;

@Component
public class ProducerService {

	@Autowired
	private KafkaTemplate<String,UserDTO> kafkaTemplate;
	
	public  void sendData(UserDTO data) {
		kafkaTemplate.send("BY", data);

	}
}