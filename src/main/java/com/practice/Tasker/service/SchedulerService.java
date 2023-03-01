package com.practice.Tasker.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SchedulerService {
    void sendCompleteTasks() throws JsonProcessingException;
    void sendIncompleteTasks() throws JsonProcessingException;
}
