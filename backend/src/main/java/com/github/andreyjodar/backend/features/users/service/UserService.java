package com.github.andreyjodar.backend.features.users.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.github.andreyjodar.backend.core.exceptions.BusinessException;
import com.github.andreyjodar.backend.core.exceptions.NotFoundException;
import com.github.andreyjodar.backend.features.roles.model.Role;
import com.github.andreyjodar.backend.features.roles.service.RoleService;
import com.github.andreyjodar.backend.features.users.model.User;
import com.github.andreyjodar.backend.features.users.model.UserRequest;
import com.github.andreyjodar.backend.features.users.model.UserResponse;
import com.github.andreyjodar.backend.features.users.repository.UserRepository;
import com.github.andreyjodar.backend.shared.service.EmailService;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RoleService roleService;

    @Autowired @Lazy
    PasswordEncoder passwordEncoder;

    public User create(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new BusinessException(messageSource.getMessage("exception.user.existemail",
                new Object[] { user.getEmail() }, LocaleContextHolder.getLocale()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userDb = userRepository.save(user);
        sendSuccessEmail(userDb);
        return userDb;
    }

    private void sendSuccessEmail(User user) {
        Context context = new Context();
        context.setVariable("name", user.getName());
        context.setVariable("createdAt", user.getCreatedAt());
        emailService.sendTemplatedEmail(user.getEmail(), "Cadastro Sucesso", context, "cadastroSucesso");
    }

    public User update(User user) {
        User userDb = findById(user.getId());
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new BusinessException(messageSource.getMessage("exception.user.existemail",
                new Object[] { user.getEmail() }, LocaleContextHolder.getLocale()));
        }
        userDb.setName(user.getName());
        userDb.setEmail(user.getEmail());
        userDb.setPassword(passwordEncoder.encode(user.getPassword()));
        userDb.setRoles(user.getRoles());
        userDb = userRepository.save(userDb);
        sendSuccessEmail(userDb);
        return userDb;
    }

    public void delete(Long id) {
        User userBanco = findById(id);
        userRepository.delete(userBanco);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.user.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.user.notfound",
                new Object[] { email }, LocaleContextHolder.getLocale())));
    }

    public Page<User> findByRoleName(String roleName, Pageable pageable) {
        return userRepository.findByRoleName(roleName, pageable);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public UserResponse fromEntity(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setRoles(user.getRoles().stream()
            .map(roleService::fromEntity).collect(java.util.stream.Collectors.toSet()));
        return userResponse;
    }

    public User fromDto(UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());

        Set<Role> roles = userRequest.getRoles().stream()
            .map(roleName -> roleService.findByName(roleName)).collect(Collectors.toSet());

        user.setRoles(roles);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("exception.user.notfound",
                new Object[] { username }, LocaleContextHolder.getLocale())));
    }
}