package com.practice.Tasker.service;

import com.practice.Tasker.payload.UserDto;
import com.practice.Tasker.payload.tasker.GlobalTaskDto;
import com.practice.Tasker.payload.tasker.ProjectDto;

import java.util.List;

public interface ProjectService {
    ProjectDto get(String name);
    List<ProjectDto> getAll();
    ProjectDto create(String name);

    ProjectDto renameProject(String name, String newName);
    void delete(String name);

    ///////

    List<UserDto> getMembers(String name);
    void addMember(String name, String username);
    void deleteMember(String name, String username);

    GlobalTaskDto createGlobalTask(String name, String globalTaskName);
    void deleteGlobalTask(String name, String globalTaskName);

}
