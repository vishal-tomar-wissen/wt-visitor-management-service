package com.wissen.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.wissen.dto.EmailDTO;

/**
 * Service class to send an email
 * 
 * @author Ankit Garg
 *
 */
@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	/**
	 * Method for sending simple e-mail message.
	 * 
	 * @param emailDTO - data to be send.
	 */
	public void sendSimpleMessage(EmailDTO emailDTO) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(emailDTO.getRecipients().stream().collect(Collectors.joining(",")));
		mailMessage.setSubject(emailDTO.getSubject());
		mailMessage.setText(emailDTO.getBody());

		emailSender.send(mailMessage);
	}

}