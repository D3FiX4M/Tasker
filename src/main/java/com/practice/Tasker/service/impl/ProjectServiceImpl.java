package com.practice.Tasker.service.impl;

import com.practice.Tasker.domain.entity.tasker.GlobalTask;
import com.practice.Tasker.domain.entity.tasker.Project;
import com.practice.Tasker.domain.entity.user.User;
import com.practice.Tasker.domain.mapper.GlobalTaskMapper;
import com.practice.Tasker.domain.mapper.ProjectMapper;
import com.practice.Tasker.domain.mapper.UserMapper;
import com.practice.Tasker.domain.repository.GlobalTaskRepository;
import com.practice.Tasker.domain.repository.ProjectRepository;
import com.practice.Tasker.domain.repository.UserRepository;
import com.practice.Tasker.exception.ExistException;
import com.practice.Tasker.exception.ForbiddenException;
import com.practice.Tasker.exception.NotFoundException;
import com.practice.Tasker.payload.UserDto;
import com.practice.Tasker.payload.tasker.GlobalTaskDto;
import com.practice.Tasker.payload.tasker.ProjectDto;
import com.practice.Tasker.service.ProjectService;
import com.practice.Tasker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final GlobalTaskRepository globalTaskRepository;
    private final UserService userService;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final GlobalTaskMapper globalTaskMapper;


    private Project findByName(String name) {
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


    @Override
    public ProjectDto get(String name) {
        return projectMapper.toDto(findByName(name));
    }

    @Override
    public List<ProjectDto> getAll() {
        List<ProjectDto> currentUserProjects = new ArrayList<>();
        User user = userService.getCurrentUser();
        for (Project project : projectRepository.findAll()
        ) {
            if (project.getMembers().contains(user)) {
                currentUserProjects.add(projectMapper.toDto(project));
            }
        }
        if (currentUserProjects.size() > 0) {
            throw new NotFoundException("PROJECTS NOT FOUND");
        } else {
            return currentUserProjects;
        }
    }

    @Override
    public ProjectDto create(String name) {
        User currentUser = userService.getCurrentUser();
        if (findByName(name).getMembers().contains(currentUser)) {
            throw new ExistException("PROJECT WITH THIS NAME ALREADY EXIST");
        }

        return projectMapper.toDto(
                projectRepository.save(
                        Project.builder()
                                .name(name)
                                .createdAt(LocalDateTime.now())
                                .author(currentUser)
                                .members(List.of(currentUser))
                                .globalTasks(new ArrayList<>())
                                .build()
                )
        );
    }

    @Override
    public ProjectDto renameProject(String name, String newName) {
        User currentUser = userService.getCurrentUser();
        Project project = findByName(name);
        if (project.getAuthor()==currentUser){
            project.setName(newName);
            projectMapper.toDto(projectRepository.save(project));
        }
        throw new ForbiddenException("YOU'RE CANNOT RENAME PROJECT");
    }

    @Override
    public void delete(String name) {
        User currentUser = userService.getCurrentUser();
        Project project = findByName(name);
        if (project.getAuthor() == currentUser) {
            projectRepository.delete(project);
        } else {
            throw new ForbiddenException("YOU'RE CANNOT DELETE PROJECT");
        }
    }

    @Override
    public List<UserDto> getMembers(String name) {
        User currentUser = userService.getCurrentUser();
        Project project = findByName(name);
        if (project.getMembers().contains(currentUser)) {
            List<UserDto> list = new ArrayList<>();
            for (User member: project.getMembers()
                 ) {
                list.add(userMapper.toDto(member));
            }
            return list;
        } else {
            throw new ForbiddenException("YOU'RE CANNOT GET MEMBERS");
        }
    }

    @Override
    public void addMember(String name, String username) {
        User currentUser = userService.getCurrentUser();
        Project project = findByName(name);

        User newMember = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new NotFoundException("USER NOT FOUND")
                );

        if (project.getAuthor() == currentUser && !project.getMembers().contains(newMember)) {
            project.getMembers().add(newMember);
            projectRepository.save(project);

        } else {
            throw new ForbiddenException("YOU'RE CANNOT GET MEMBERS");
        }
    }

    @Override
    public void deleteMember(String name, String username) {
        User currentUser = userService.getCurrentUser();
        Project project = findByName(name);
        User member = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new NotFoundException("USER NOT FOUND")
                );
        if (project.getMembers().contains(member) && project.getAuthor() == currentUser) {
            project.getMembers().remove(member);
            projectRepository.save(project);
        }
        throw new ForbiddenException("YOU'RE CANNOT DELETE MEMBERS");
    }

    @Override
    public GlobalTaskDto createGlobalTask(String name, String globalTaskName) {
        User currentUser = userService.getCurrentUser();
        Project project = findByName(name);
        if (project.getAuthor() == currentUser) {

            for (GlobalTask globalTask : project.getGlobalTasks()
            ) {
                if (globalTask.getName().equals(globalTaskName)){
                    throw new ExistException("GLOBAL TASK WITH NAME ALREADY EXIST");
                }
            }

            GlobalTask globalTask = GlobalTask.builder()
                    .name(globalTaskName)
                    .author(currentUser)
                    .createdAt(LocalDateTime.now())
                    .tasks(new ArrayList<>())
                    .build();

            project.getGlobalTasks().add(globalTask);
            projectRepository.save(project);
            return globalTaskMapper.toDto(globalTask);
        }
        throw new ForbiddenException("YOU'RE CANNOT CREATE GLOBAL TASK");
    }

    @Override
    public void deleteGlobalTask(String name, String globalTaskName) {
        User currentUser = userService.getCurrentUser();
        Project project = findByName(name);
        if (project.getAuthor() == currentUser) {

            for (GlobalTask globalTask : project.getGlobalTasks()
            ) {
                if (globalTask.getName().equals(globalTaskName)){
                    project.getGlobalTasks().remove(globalTask);
                    globalTaskRepository.delete(globalTask);
                    projectRepository.save(project);
                }
            }
            throw new NotFoundException("GLOBAL TASK WITH NAME NOT FOUND");
        }
        throw new ForbiddenException("YOU'RE CANNOT DELETE GLOBAL TASK");
    }
}
