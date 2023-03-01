package com.practice.Tasker.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.Tasker.domain.entity.tasker.GlobalTask;
import com.practice.Tasker.domain.entity.tasker.Project;
import com.practice.Tasker.domain.entity.tasker.Task;
import com.practice.Tasker.domain.entity.user.User;
import com.practice.Tasker.domain.repository.ProjectRepository;
import com.practice.Tasker.service.EmailService;
import com.practice.Tasker.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {
    private final ProjectRepository projectRepository;
    private final EmailService emailService;


    @Override
    @Scheduled(cron = "@daily")
    @Async
    public void sendCompleteTasks() throws JsonProcessingException {
        for (Project project : projectRepository.findAll()) {

            for (User user : project.getMembers()) {

                int count = 0;

                for (GlobalTask globalTask : project.getGlobalTasks()) {

                    for (Task task : globalTask.getTasks()) {
                        if (task.isStatus() && task.getMembers().contains(user)) {
                            count++;
                        }
                    }
                }
                if (count > 0) {
                    emailService.sendSchedulerCompleteMessage(user.getMail(), count);
                }
            }
        }
    }

    @Override
    @Scheduled(cron = "@daily")
    public void sendIncompleteTasks() throws JsonProcessingException {
        for (Project project : projectRepository.findAll()) {

            for (User user : project.getMembers()) {

                int count = 0;

                for (GlobalTask globalTask : project.getGlobalTasks()) {

                    for (Task task : globalTask.getTasks()) {
                        if (!(task.isStatus()) && task.getMembers().contains(user)) {
                            count++;
                        }
                    }
                }
                if (count > 0) {
                    emailService.sendSchedulerInCompleteMessage(user.getMail(), count);
                }
            }
        }
    }
}
