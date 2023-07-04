package com.wissen.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.wissen.dto.EmailDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	public void sendEmail(EmailDTO emailDTO) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		Context context = new Context();
		context.setVariables(emailDTO.getProperties());
		helper.setTo(emailDTO.getRecipients().stream().collect(Collectors.joining(",")));
		helper.setSubject(emailDTO.getSubject());

		String html = templateEngine.process(emailDTO.getTemplateName(), context);
		helper.setText(html, true);

		log.info("Sending email using template.");
		emailSender.send(message);
	}
}
