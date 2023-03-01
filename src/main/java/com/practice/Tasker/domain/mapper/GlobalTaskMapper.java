package com.practice.Tasker.domain.mapper;

import com.practice.Tasker.domain.entity.tasker.GlobalTask;
import com.practice.Tasker.payload.tasker.GlobalTaskDto;
import com.practice.Tasker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GlobalTaskMapper {

    private final UserService userService;


    public GlobalTask toEntity(GlobalTaskDto dto){
        return GlobalTask.builder()
                .name(dto.getName())
                .author(userService.get(dto.getAuthor_id()))
                .createdAt(dto.getCreatedAt())
                .tasks(dto.getTasks())
                .build();
    }

    public GlobalTaskDto toDto(GlobalTask task){
        return GlobalTaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .author_id(task.getAuthor().getId())
                .createdAt(task.getCreatedAt())
                .tasks(task.getTasks())
                .build();
    }

    public GlobalTask replace(GlobalTask task, GlobalTaskDto dto){
        task.setName(dto.getName());
        return task;
    }
}
