package com.practice.Tasker.domain.entity.tasker;

import com.practice.Tasker.domain.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private boolean status = false;
    @ManyToOne
    @JoinColumn(name = "global_task_id")
    private GlobalTask globalTask;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String description;
    @ManyToMany
    @JoinTable(name = "tasks_users",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members = new ArrayList<>();
}
