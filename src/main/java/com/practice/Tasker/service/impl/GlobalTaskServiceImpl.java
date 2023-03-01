package com.practice.Tasker.service.impl;

import com.practice.Tasker.domain.entity.tasker.GlobalTask;
import com.practice.Tasker.domain.entity.tasker.Project;
import com.practice.Tasker.domain.entity.tasker.Task;
import com.practice.Tasker.domain.entity.user.User;
import com.practice.Tasker.domain.mapper.GlobalTaskMapper;
import com.practice.Tasker.domain.mapper.TaskMapper;
import com.practice.Tasker.domain.repository.GlobalTaskRepository;
import com.practice.Tasker.domain.repository.ProjectRepository;
import com.practice.Tasker.domain.repository.TaskRepository;
import com.practice.Tasker.domain.repository.UserRepository;
import com.practice.Tasker.exception.ExistException;
import com.practice.Tasker.exception.ForbiddenException;
import com.practice.Tasker.exception.NotFoundException;
import com.practice.Tasker.payload.tasker.GlobalTaskDto;
import com.practice.Tasker.payload.tasker.TaskDto;
import com.practice.Tasker.service.GlobalTaskService;
import com.practice.Tasker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class GlobalTaskServiceImpl implements GlobalTaskService {

    private final ProjectRepository projectRepository;
    private final GlobalTaskRepository globalTaskRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final GlobalTaskMapper globalTaskMapper;
    private final UserService userService;
    private final TaskMapper taskMapper;


    private Project findByProjectName(String name) {
        Project project = projectRepository.findByName(name)
                .orElseThrow(
                        () -> new NotFoundException("PROJECT NOT FOUND")
                );
        if (project.getMembers().contains(userService.getCurrentUser())) {
            return project;
        } else {
            throw new NotFoundException("YOU'RE NOT PART ON THIS PROJECT");
        }
    }

    private GlobalTask findByName(Project project, String name) {
        for (GlobalTask globalTask : project.getGlobalTasks()
        ) {
            if (globalTask.getName().equals(name)) {
                return globalTask;
            }
        }
        throw new NotFoundException("GLOBAL TASK NOT FOUND");
    }

    @Override
    public GlobalTaskDto get(String projectName, String name) {
        Project project = findByProjectName(projectName);
        GlobalTask globalTask = findByName(project, name);
        return globalTaskMapper.toDto(globalTask);
    }

    @Override
    public GlobalTaskDto renameGlobalTask(String projectName, String name, String newName) {
        Project project = findByProjectName(projectName);
        GlobalTask globalTask = findByName(project, name);
        if (project.getAuthor() == userService.getCurrentUser()) {
            globalTask.setName(newName);
            globalTaskMapper.toDto(globalTaskRepository.save(globalTask));
        }
        throw new ForbiddenException("YOU'RE CANNOT RENAME GLOBAL TASK");
    }

    @Override
    public TaskDto createTask(String projectName, String name, String taskName, String taskDescription) {
        Project project = findByProjectName(projectName);
        GlobalTask globalTask = findByName(project, name);

        for (Task task : globalTask.getTasks()
        ) {
            if (task.getName().equals(taskName)) {
                throw new ExistException("TASK WITH NAME ALREADY EXIST");
            }
        }
        if (project.getAuthor() == userService.getCurrentUser()) {
            Task task = Task.builder()
                    .name(taskName)
                    .status(false)
                    .description(taskDescription)
                    .author(globalTask.getAuthor())
                    .createdAt(LocalDateTime.now())
                    .members(new ArrayList<>())
                    .build();
            taskRepository.save(task);
            globalTask.getTasks().add(task);
            globalTaskRepository.save(globalTask);
            return taskMapper.toDto(task);
        }
        throw new ForbiddenException("YOU'RE CANNOT ADD TASK");
    }

    @Override
    public TaskDto renameTask(String projectName, String name, String taskName, String newName) {
        Project project = findByProjectName(projectName);
        GlobalTask globalTask = findByName(project, name);
        for (Task task : globalTask.getTasks()
        ) {
            if (task.getName().equals(taskName) && project.getAuthor() == userService.getCurrentUser()) {
                task.setName(newName);
                return taskMapper.toDto(taskRepository.save(task));
            }
        }
        throw new ForbiddenException("YOU'RE CANNOT RENAME TASK");
    }

    @Override
    public TaskDto changeDescription(String projectName, String name, String taskName, String newTaskDescription) {
        Project project = findByProjectName(projectName);
        GlobalTask globalTask = findByName(project, name);
        for (Task task : globalTask.getTasks()
        ) {
            if (task.getName().equals(taskName) && project.getAuthor() == userService.getCurrentUser()) {
                task.setDescription(newTaskDescription);
                return taskMapper.toDto(taskRepository.save(task));
            }
        }
        throw new ForbiddenException("YOU'RE CANNOT RENAME TASK DESCRIPTION");
    }

    @Override
    public void deleteTask(String projectName, String name, String taskName) {
        Project project = findByProjectName(projectName);
        GlobalTask globalTask = findByName(project, name);
        for (Task task : globalTask.getTasks()
        ) {
            if (task.getName().equals(taskName)) {
                if (project.getAuthor() == userService.getCurrentUser()) {
                    globalTask.getTasks().remove(task);
                    taskRepository.delete(task);
                    return;
                } else {
                    throw new ForbiddenException("YOU'RE CANNOT DELETE TASK");
                }
            }
        }
        throw new NotFoundException("TASK NOT FOUND");
    }

    @Override
    public void addMember(String projectName, String name, String taskName, String username) {
        Project project = findByProjectName(projectName);
        GlobalTask globalTask = findByName(project, name);
        for (Task task : globalTask.getTasks()
        ) {
            if (task.getName().equals(taskName)) {

                User newMember = userRepository.findByUsername(username)
                        .orElseThrow(
                                () -> new NotFoundException("USER NOT FOUND")
                        );

                if (project.getAuthor() == userService.getCurrentUser() && !task.getMembers().contains(newMember)) {
                    task.getMembers().add(newMember);
                    taskRepository.save(task);
                    return;
                } else {
                    throw new ForbiddenException("YOU'RE CANNOT ADD MEMBER");
                }
            }
        }
    }

    @Override
    public void deleteMember(String projectName, String name, String taskName, String username) {
        Project project = findByProjectName(projectName);
        GlobalTask globalTask = findByName(project, name);
        for (Task task : globalTask.getTasks()
        ) {
            if (task.getName().equals(taskName)) {
                User newMember = userRepository.findByUsername(username)
                        .orElseThrow(
                                () -> new NotFoundException("USER NOT FOUND")
                        );

                if (project.getAuthor() == userService.getCurrentUser() && task.getMembers().contains(newMember)) {
                    task.getMembers().remove(newMember);
                    taskRepository.save(task);
                    return;
                } else {
                    throw new ForbiddenException("YOU'RE CANNOT DELETE MEMBER");
                }
            }
        }
    }

    @Override
    public void finishTask(String projectName, String name, String taskName) {
        Project project = findByProjectName(projectName);
        GlobalTask globalTask = findByName(project, name);
        for (Task task : globalTask.getTasks()
        ) {
            if (task.getName().equals(taskName) && (task.getMembers().contains(userService.getCurrentUser()) || task.getAuthor() == userService.getCurrentUser())) {
                task.setStatus(true);
                taskRepository.save(task);
            }
        }
    }
}
