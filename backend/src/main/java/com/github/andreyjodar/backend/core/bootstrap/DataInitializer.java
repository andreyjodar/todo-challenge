package com.github.andreyjodar.backend.core.bootstrap;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.andreyjodar.backend.features.labels.model.Label;
import com.github.andreyjodar.backend.features.labels.repository.LabelRepository;
import com.github.andreyjodar.backend.features.roles.model.Role;
import com.github.andreyjodar.backend.features.roles.repository.RoleRepository;
import com.github.andreyjodar.backend.features.tasks.model.Task;
import com.github.andreyjodar.backend.features.tasks.repository.TaskRepository;
import com.github.andreyjodar.backend.features.users.model.User;
import com.github.andreyjodar.backend.features.users.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final LabelRepository labelRepository;
    private final TaskRepository taskRepository;
    private PasswordEncoder passwordEncoder;

    public DataInitializer(
            RoleRepository roleRepository, 
            UserRepository userRepository, 
            LabelRepository labelRepository, 
            TaskRepository taskRepository, 
            PasswordEncoder passwordEncoder) {

        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.labelRepository = labelRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
            .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

        Role userRole = roleRepository.findByName("ROLE_USER")
            .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        User admin = userRepository.findByEmail("develop.email.sender@gmail.com")
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setName("Admin Develop");
                newUser.setEmail("develop.email.sender@gmail.com");
                newUser.setPassword(passwordEncoder.encode("Develop25#")); 
                newUser.getRoles().add(adminRole);
                newUser.getRoles().add(userRole);
                return userRepository.save(newUser);
            }
        );

        Label collegeLabel = labelRepository.findByName("College")
            .orElseGet(() -> labelRepository.save(new Label("College", "College Tasks and Academic Activities")));

        Label churchLabel = labelRepository.findByName("Church")
            .orElseGet(() -> labelRepository.save(new Label("Church", "Church Tasks and Social Activities")));

        Label gymLabel = labelRepository.findByName("Gym")
            .orElseGet(() -> labelRepository.save(new Label("Gym", "Gym Tasks and Physical Activities")));

        taskRepository.findById(1L)
            .orElseGet(() -> {
                Task task = new Task();
                task.setTitle("Simple Title");
                task.setDescription("Simple description for a simple task");
                task.setAuthor(admin);
                task.getLabels().add(collegeLabel);
                task.getLabels().add(gymLabel);
                task.setDeadline(LocalDate.now().plusDays(30));
                return taskRepository.save(task);
            });
    }
}
