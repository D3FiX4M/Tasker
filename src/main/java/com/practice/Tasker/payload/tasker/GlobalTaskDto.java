package com.practice.Tasker.payload.tasker;

import com.practice.Tasker.domain.entity.tasker.Task;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalTaskDto {
    private Long id;
    private String name;
    private Long author_id;
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<Task> tasks;
}
