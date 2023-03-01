package com.practice.Tasker.payload.tasker;

import com.practice.Tasker.domain.entity.tasker.GlobalTask;
import com.practice.Tasker.domain.entity.user.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    private Long author_id;
    private List<User> members;
    private LocalDateTime createdAt;
    private List<GlobalTask> globalTasks;
}
