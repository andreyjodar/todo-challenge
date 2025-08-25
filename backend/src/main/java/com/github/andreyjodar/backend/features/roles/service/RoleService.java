package com.github.andreyjodar.backend.features.roles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.core.exceptions.NotFoundException;
import com.github.andreyjodar.backend.features.roles.model.Role;
import com.github.andreyjodar.backend.features.roles.repository.RoleRepository;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    MessageSource messageSource;

    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.role.notfound",
                new Object[] { roleName }, LocaleContextHolder.getLocale())));
    }

    public Role findById(Long id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.role.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }
}
