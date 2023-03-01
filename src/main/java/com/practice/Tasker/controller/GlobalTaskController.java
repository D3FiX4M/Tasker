package com.practice.Tasker.controller;

import com.practice.Tasker.payload.tasker.GlobalTaskDto;
import com.practice.Tasker.payload.tasker.TaskDto;
import com.practice.Tasker.service.GlobalTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GlobalTaskController {
    private final GlobalTaskService globalTaskService;

    private final static String GET_GLOBAL_TASK = "/api/project/{projectName}/global-task/{name}";
    private final static String TASK = GET_GLOBAL_TASK + "/task";
    private final static String DESCRIPTION = TASK + "/description";
    private final static String MEMBERS = TASK + "/members";
    private final static String FINISH = TASK + "/finish";


    @GetMapping(GET_GLOBAL_TASK)
    public GlobalTaskDto get(
            @PathVariable String projectName,
            @PathVariable String name) {
        return globalTaskService.get(projectName, name);
    }

    @PutMapping(GET_GLOBAL_TASK)
    public GlobalTaskDto renameGlobalTask(
            @PathVariable String projectName,
            @PathVariable String name,
            @RequestParam String newName
    ) {
        return globalTaskService.renameGlobalTask(projectName, name, newName);
    }

    @PostMapping(TASK)
    public TaskDto createTask(
            @PathVariable String projectName,
            @PathVariable String name,
            @RequestParam String taskName,
            @RequestParam String taskDescription
    ) {
        return globalTaskService.createTask(projectName, name, taskName, taskDescription);
    }

    @PutMapping(TASK)
    public TaskDto renameTask(
            @PathVariable String projectName,
            @PathVariable String name,
            @RequestParam String taskName,
            @RequestParam String newTaskName
    ) {
        return globalTaskService.renameTask(projectName, name, taskName, newTaskName);
    }

    @PutMapping(DESCRIPTION)
    public TaskDto changeDescription(
            @PathVariable String projectName,
            @PathVariable String name,
            @RequestParam String taskName,
            @RequestParam String newTaskDescription
    ) {
        return globalTaskService.changeDescription(projectName, name, taskName, newTaskDescription);
    }

    @DeleteMapping(TASK)
    public void deleteTask(
            @PathVariable String projectName,
            @PathVariable String name,
            @RequestParam String taskName
    ) {
        globalTaskService.deleteTask(projectName, name, taskName);
    }

    @PutMapping(MEMBERS)
    void addMember(@PathVariable String projectName,
                   @PathVariable String name,
                   @RequestParam String taskName,
                   @RequestParam String username
    ) {
        globalTaskService.addMember(projectName, name, taskName, username);
    }

    @DeleteMapping(MEMBERS)
    void deleteMember(@PathVariable String projectName,
                      @PathVariable String name,
                      @RequestParam String taskName,
                      @RequestParam String username
    ) {
        globalTaskService.deleteMember(projectName, name, taskName, username);
    }
    @PutMapping(FINISH)
    void finishTask(@PathVariable String projectName,
                    @PathVariable String name,
                    @RequestParam String taskName
    ) {
        globalTaskService.finishTask(projectName, name, taskName);
    }

}
