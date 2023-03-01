package com.practice.Tasker.domain.mapper;

import com.practice.Tasker.domain.entity.user.User;
import com.practice.Tasker.payload.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .mail(user.getMail())
                .roles(user.getRoles())
                .build();
    }
}
