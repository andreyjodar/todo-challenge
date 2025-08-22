package com.github.andreyjodar.backend.core.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.andreyjodar.backend.features.roles.model.Role;
import com.github.andreyjodar.backend.features.roles.repository.RoleRepository;
import com.github.andreyjodar.backend.features.users.model.User;
import com.github.andreyjodar.backend.features.users.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
            .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

        Role userRole = roleRepository.findByName("ROLE_USER")
            .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        userRepository.findByEmail("develop.email.sender@gmail.com")
            .orElseGet(() -> {
                User admin = new User();
                admin.setName("Admin Develop");
                admin.setEmail("develop.email.sender@gmail.com");
                admin.setPassword(passwordEncoder.encode("Develop25#")); 
                admin.getRoles().add(adminRole);
                admin.getRoles().add(userRole);
                return userRepository.save(admin);
            }
        );
    }
}
