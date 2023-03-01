package com.practice.Tasker.service;

import com.practice.Tasker.payload.tasker.GlobalTaskDto;
import com.practice.Tasker.payload.tasker.TaskDto;

public interface GlobalTaskService {
    GlobalTaskDto get(String projectName, String name);

    GlobalTaskDto renameGlobalTask(String projectName, String name, String newName);

    TaskDto createTask(String projectName, String name, String taskName, String taskDescription);

    TaskDto renameTask(String projectName, String name, String taskName, String newName);
    TaskDto changeDescription(String projectName, String name, String taskName, String newTaskDescription);

    void deleteTask(String projectName, String name, String taskName);

    void addMember(String projectName, String name, String taskName, String username);

    void deleteMember(String projectName, String name, String taskName, String username);

    void finishTask(String projectName, String name, String taskName);
}
