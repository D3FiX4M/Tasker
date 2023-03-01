package com.practice.Tasker.domain.mapper;

import com.practice.Tasker.domain.entity.tasker.Task;
import com.practice.Tasker.payload.tasker.TaskDto;
import com.practice.Tasker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final UserService userService;


    public Task toEntity(TaskDto dto) {
        return Task.builder()
                .name(dto.getName())
                .status(dto.isStatus())
                .author(userService.get(dto.getAuthor_id()))
                .createdAt(dto.getCreatedAt())
                .description(dto.getDescription())
                .members(dto.getMembers())
                .build();
    }

    public TaskDto toDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .status(task.isStatus())
                .author_id(task.getAuthor().getId())
                .createdAt(task.getCreatedAt())
                .description(task.getDescription())
                .members(task.getMembers())
                .build();
    }

    public Task replace(Task task, TaskDto dto) {
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        return task;
    }
}
