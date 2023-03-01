package com.practice.Tasker.payload.tasker;

import com.practice.Tasker.domain.entity.user.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String name;
    private boolean status;
    private Long author_id;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String description;
    private List<User> members;
}
