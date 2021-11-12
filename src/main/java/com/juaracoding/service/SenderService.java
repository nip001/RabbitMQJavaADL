package com.juaracoding.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juaracoding.model.MessageModel;

@Service
public class SenderService {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Value("${rabbit.queue}")
	String queueName;

	@Value("${rabbit.exchange}")
	String exchange;
	@Value("${rabbit.routingkey}")
	String routingKey;
	
	public void publish(MessageModel msg) {
		rabbitTemplate.convertAndSend(exchange, routingKey, msg);
		System.out.println("msg = "+msg);
//		Message msg2 = (Message) rabbitTemplate.receiveAndConvert(queueName);
//
//		System.out.println("msg = "+msg2.getIdPengirim());
	}
}
