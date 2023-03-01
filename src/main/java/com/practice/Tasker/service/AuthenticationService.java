package com.practice.Tasker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.Tasker.payload.AuthenticationRequest;
import com.practice.Tasker.payload.RegistrationRequest;

public interface AuthenticationService {
    String signIn(AuthenticationRequest request);
    String signUp(RegistrationRequest request) throws JsonProcessingException;
}
