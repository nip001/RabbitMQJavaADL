package com.juaracoding.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.juaracoding.model.MessageModel;
import com.juaracoding.repository.MessageRepository;

@Service
public class RabbitMQListener implements MessageListener{

	MessageRepository mRepo;
	
	public RabbitMQListener(MessageRepository mRepo) {
		// TODO Auto-generated constructor stub
		this.mRepo = mRepo;
	}
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		String data = new String(message.getBody());
		Gson gson = new Gson();
		MessageModel message2 = gson.fromJson(data, MessageModel.class); 
		mRepo.save(message2);
	}

}
