package com.juaracoding.config;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.juaracoding.repository.MessageRepository;
import com.juaracoding.service.RabbitMQListener;


@Configuration
public class RabbitMQConfig {

	@Value("${rabbit.queue}")
	String queueName;
	@Value("${rabbit.exchange}")
	String exchange;
	@Value("${rabbit.routingkey}")
	String routingKey;

	@Autowired
	private MessageRepository mRepo;
	
	@Bean
	Queue queue() {
		return new Queue(queueName,false);
	}
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}
	
	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}
	
	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	MessageListenerContainer messageListenerContainer(ConnectionFactory cFactory) {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setConnectionFactory(cFactory);
		simpleMessageListenerContainer.setQueues(queue());
		simpleMessageListenerContainer.setMessageListener(new RabbitMQListener(mRepo));
		return simpleMessageListenerContainer;
	}
	
	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory cFactory) {
		RabbitTemplate template = new RabbitTemplate(cFactory);
		template.setMessageConverter(messageConverter());
		return template;
	}
}
