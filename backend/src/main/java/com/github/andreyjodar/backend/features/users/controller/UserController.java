package com.github.andreyjodar.backend.features.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.core.exceptions.ForbiddenException;
import com.github.andreyjodar.backend.features.users.model.User;
import com.github.andreyjodar.backend.features.users.model.UserRequest;
import com.github.andreyjodar.backend.features.users.model.UserResponse;
import com.github.andreyjodar.backend.features.users.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserResponse> create(
            @Valid @RequestBody UserRequest userRequest) {

        User user = userService.fromDto(userRequest);
        User userDb = userService.create(user);
        return ResponseEntity.ok(userService.fromEntity(userDb));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> findAll(
            Pageable pageable) {

        Page<User> users = userService.findAll(pageable);
        Page<UserResponse> usersResponse = users.map(userService::fromEntity);
        return ResponseEntity.ok(usersResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserResponse> update(
            @PathVariable("id") Long id, 
            @Valid @RequestBody UserRequest userRequest, 
            @AuthenticationPrincipal User principal) {

        if(!isAdmin(principal) && !principal.getId().equals(id)) {
            throw new ForbiddenException(messageSource.getMessage("exception.userrole.unauthorized",
                new Object[] { principal.getAuthorities() }, LocaleContextHolder.getLocale()));
        }
        User user = userService.fromDto(userRequest);
        user.setId(id);
        return ResponseEntity.ok(userService.fromEntity(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<String> delete(
            @PathVariable("id") Long id, 
            @AuthenticationPrincipal User principal) {

        if(!isAdmin(principal) && !principal.getId().equals(id)) {
            throw new ForbiddenException(messageSource.getMessage("exception.userrole.unauthorized",
                new Object[] { principal.getAuthorities() }, LocaleContextHolder.getLocale()));
        }
        userService.delete(id);
        return ResponseEntity.ok("User with id:" + id + " deleted successfully");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserResponse> findById(
            @PathVariable("id") Long id, 
            @AuthenticationPrincipal User principal) {

        if(!isAdmin(principal) && !principal.getId().equals(id)) {
            throw new ForbiddenException(messageSource.getMessage("exception.userrole.unauthorized",
                new Object[] { principal.getAuthorities() }, LocaleContextHolder.getLocale()));
        }
        User user = userService.findById(id);
        return ResponseEntity.ok(userService.fromEntity(user));
    }

    @GetMapping("/by-role")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> findByRole(
            @RequestParam String role, 
            Pageable pageable) {

        Page<User> users = userService.findByRoleName(role, pageable);
        Page<UserResponse> usersResponse = users.map(userService::fromEntity);
        return ResponseEntity.ok(usersResponse);
    }

    private boolean isAdmin(User principal) {
        return principal.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }
}
