package com.practice.Tasker.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface EmailService {
    void sendRegistrationMessage(String userEmail) throws JsonProcessingException;
    void sendSchedulerCompleteMessage(String userEmail, int count) throws JsonProcessingException;
    void sendSchedulerInCompleteMessage(String userEmail, int count) throws JsonProcessingException;
}
