package com.practice.Tasker.service.impl;

import com.practice.Tasker.domain.entity.user.User;
import com.practice.Tasker.domain.repository.UserRepository;
import com.practice.Tasker.exception.NotFoundException;
import com.practice.Tasker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new NotFoundException("USER NOT FOUND")
                );
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        ()-> new NotFoundException("USER NOT FOUND")
                );
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (principal instanceof User) {
            username = ((User) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new NotFoundException("USER NOT FOUND")
                );
    }
}
