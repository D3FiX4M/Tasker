package com.practice.Tasker.domain.mapper;

import com.practice.Tasker.domain.entity.tasker.Project;
import com.practice.Tasker.payload.tasker.ProjectDto;
import com.practice.Tasker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

    private final UserService userService;


    public Project toEntity(ProjectDto dto){
        return Project.builder()
                .name(dto.getName())
                .author(userService.get(dto.getAuthor_id()))
                .createdAt(dto.getCreatedAt())
                .members(dto.getMembers())
                .globalTasks(dto.getGlobalTasks())
                .build();
    }

    public ProjectDto toDto(Project project){
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .author_id(project.getAuthor().getId())
                .createdAt(project.getCreatedAt())
                .members(project.getMembers())
                .globalTasks(project.getGlobalTasks())
                .build();
    }

    public Project replace(Project project, ProjectDto dto){
        project.setName(dto.getName());
        return project;
    }
}
