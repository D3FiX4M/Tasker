package com.practice.Tasker.service;

import com.practice.Tasker.domain.entity.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User get(Long id);
    User getCurrentUser();

}
