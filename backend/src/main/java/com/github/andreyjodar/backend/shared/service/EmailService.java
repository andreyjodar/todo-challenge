package com.github.andreyjodar.backend.shared.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMail;

    @Autowired
    private TemplateEngine templateEngine;

    @Async
    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage simpleMail = new SimpleMailMessage();
        simpleMail.setTo(to);
        simpleMail.setSubject(subject);
        simpleMail.setText(text);
        javaMail.send(simpleMail);
    }

    @Async
    public void sendTemplatedEmail(String to, String subject, Context emailContext, String template) {
        String process = templateEngine.process(template, emailContext);
        MimeMessage message = javaMail.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(process, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMail.send(message);
    }
}