package com.practice.Tasker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.Tasker.payload.AuthenticationRequest;
import com.practice.Tasker.payload.RegistrationRequest;
import com.practice.Tasker.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    private final static String SIGN_IN = "/api/sign-in";
    private final static String SIGN_UP = "/api/sign-up";

    @PostMapping(SIGN_IN)
    public String signIn(@RequestBody AuthenticationRequest request) {
        return authenticationService.signIn(request);
    }

    @PostMapping(SIGN_UP)
    public String signUp(@RequestBody RegistrationRequest request) throws JsonProcessingException {
        return authenticationService.signUp(request);
    }

}
