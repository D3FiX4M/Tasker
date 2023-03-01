package com.practice.Tasker.payload;

import com.practice.Tasker.domain.entity.user.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String mail;
    private Set<Role> roles;
}
