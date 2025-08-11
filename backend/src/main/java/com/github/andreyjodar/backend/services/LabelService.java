package com.github.andreyjodar.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.repositories.LabelRepository;

@Service
public class LabelService {
    @Autowired
    LabelRepository labelRepository;

    @Autowired
    MessageSource messageSource;
}
