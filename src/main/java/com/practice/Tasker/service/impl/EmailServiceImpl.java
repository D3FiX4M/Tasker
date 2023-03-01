package com.practice.Tasker.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.Tasker.payload.MailMessage;
import com.practice.Tasker.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@EnableRabbit
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "email_sender")
    private void sendMessageFromQueue(String jsonMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MailMessage message = objectMapper.readValue(jsonMessage, MailMessage.class);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText(message.getText());
        simpleMailMessage.setTo(message.getTo());
        simpleMailMessage.setSubject(message.getSubject());
        javaMailSender.send(simpleMailMessage);
    }

    private void sendMessageToQueue(MailMessage message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(message);
        rabbitTemplate.convertAndSend("email_sender", jsonMessage);
    }

    @Override
    public void sendRegistrationMessage(String userEmail) throws JsonProcessingException {
        MailMessage message = new MailMessage();
        message.setTo(userEmail);
        message.setSubject("Registration");
        message.setText("You have successfully registered on the service");
        sendMessageToQueue(message);
    }

    @Override
    public void sendSchedulerCompleteMessage(String userEmail, int count) throws JsonProcessingException {
        MailMessage message = new MailMessage();
        message.setTo(userEmail);
        message.setSubject("Daily Positive Result");
        message.setText("You completed " + count + " tasks in the past day");
        sendMessageToQueue(message);
    }

    @Override
    public void sendSchedulerInCompleteMessage(String userEmail, int count) throws JsonProcessingException {
        MailMessage message = new MailMessage();
        message.setTo(userEmail);
        message.setSubject("Daily Negative Result");
        message.setText("You have " + count + " unfinished tasks in the past day");
        sendMessageToQueue(message);
    }
}
