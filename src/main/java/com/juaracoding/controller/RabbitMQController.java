package com.juaracoding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juaracoding.model.MessageModel;
import com.juaracoding.service.SenderService;

@RestController
public class RabbitMQController {

	@Autowired
	SenderService sendService;
	
	@GetMapping("/")
	public String rabbitSend(@RequestParam("idPengirim") int idPengirim, @RequestParam("message") String message) {
		MessageModel msg = new MessageModel();
		msg.setIdPengirim(idPengirim);
		msg.setMessage(message);
		sendService.publish(msg);
		return "Message berhasil dikirim";
	}
}
