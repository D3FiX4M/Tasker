package com.practice.Tasker.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.Tasker.domain.entity.user.Role;
import com.practice.Tasker.domain.entity.user.User;
import com.practice.Tasker.domain.repository.RoleRepository;
import com.practice.Tasker.domain.repository.UserRepository;
import com.practice.Tasker.exception.ExistException;
import com.practice.Tasker.exception.NotFoundException;
import com.practice.Tasker.payload.AuthenticationRequest;
import com.practice.Tasker.payload.RegistrationRequest;
import com.practice.Tasker.service.AuthenticationService;
import com.practice.Tasker.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;


    private void userExist(String username, String mail){
        if (userRepository.existsByUsername(username)||userRepository.existsByMail(mail)) {
            throw new ExistException("USER WITH THIS CREDENTIALS ALREADY EXIST");
        }
    }

    @Override
    public String signIn(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "USER SUCCESSFULLY AUTHENTICATED";

    }

    @Override
    public String signUp(RegistrationRequest request) throws JsonProcessingException {
        userExist(request.getUsername(), request.getPassword());
        Role role = roleRepository.findById(1L)
                .orElseThrow(
                        ()-> new NotFoundException("ROLE NOT FOUND")
                );
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .mail(request.getMail())
                .roles(roles)
                .build();
        userRepository.save(user);

        emailService.sendRegistrationMessage(user.getMail());

        return signIn(new AuthenticationRequest(request.getUsername(), request.getPassword()));
    }
}
