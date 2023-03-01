package com.practice.Tasker.controller;

import com.practice.Tasker.domain.entity.tasker.Project;
import com.practice.Tasker.payload.UserDto;
import com.practice.Tasker.payload.tasker.GlobalTaskDto;
import com.practice.Tasker.payload.tasker.ProjectDto;
import com.practice.Tasker.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    private static final String ROOT = "/api";
    private static final String MAIN = ROOT + "/project";
    private static final String GET_PROJECT = MAIN + "/{name}";
    private static final String MEMBERS = GET_PROJECT + "/members";
    private static final String GLOBAL_TASK = GET_PROJECT + "/global-task";

    @GetMapping(MAIN)
    public List<ProjectDto> getAll() {
        return projectService.getAll();
    }

    @GetMapping(GET_PROJECT)
    public ProjectDto get(@PathVariable String name) {
        return projectService.get(name);
    }

    @PostMapping(GET_PROJECT)
    public ProjectDto create(@PathVariable String name) {
        return projectService.create(name);
    }

    @PutMapping(GET_PROJECT)
    public ProjectDto renameProject(@PathVariable String name,
                                    @RequestParam String newName){
        return projectService.renameProject(name, newName);
    }

    @DeleteMapping(GET_PROJECT)
    public void delete(@PathVariable String name) {
        projectService.delete(name);
    }

    @GetMapping(MEMBERS)
    public List<UserDto> getMembers(@PathVariable String name) {
        return projectService.getMembers(name);
    }

    @PostMapping(MEMBERS)
    public void addMember(
            @PathVariable String name,
            @RequestParam String username
    ) {
        projectService.addMember(name, username);
    }

    @DeleteMapping(MEMBERS)
    public void deleteMember(
            @PathVariable String name,
            @RequestParam String username
    ) {
        projectService.deleteMember(name, username);
    }

    @PostMapping(GLOBAL_TASK)
    public GlobalTaskDto createGlobalTask(
            @PathVariable String name,
            @RequestParam String globalTaskName
    ) {
        return projectService.createGlobalTask(name, globalTaskName);
    }

    @DeleteMapping(GLOBAL_TASK)
    public void deleteGlobalTask(
            @PathVariable String name,
            @RequestParam String globalTaskName
    ) {
        projectService.deleteGlobalTask(name, globalTaskName);
    }


}
