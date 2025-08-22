package com.github.andreyjodar.backend.features.labels.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.core.exceptions.BusinessException;
import com.github.andreyjodar.backend.core.exceptions.NotFoundException;
import com.github.andreyjodar.backend.features.labels.model.Label;
import com.github.andreyjodar.backend.features.labels.model.LabelResponse;
import com.github.andreyjodar.backend.features.labels.repository.LabelRepository;

@Service
public class LabelService {
    @Autowired
    LabelRepository labelRepository;

    @Autowired
    MessageSource messageSource;

    public Label create(Label label) {
        if(labelRepository.findByName(label.getName()).isPresent()) {
            throw new BusinessException(messageSource.getMessage("exception.label.existname", 
                new Object[] { label.getName() }, LocaleContextHolder.getLocale()));
        }
        return labelRepository.save(label);
    }

    public Label update(Label label) {
        Label labelDb = findById(label.getId());
        if(labelRepository.findByName(label.getName()).isPresent()) {
            throw new BusinessException(messageSource.getMessage("exception.label.existname", 
                new Object[] { label.getName() }, LocaleContextHolder.getLocale()));
        }
        labelDb.setName(label.getName());
        labelDb.setDescription(label.getDescription());
        return labelRepository.save(labelDb);
    }

    public void delete(Long id) {
        Label labelDb = findById(id);
        labelRepository.delete(labelDb);
    }

    public Label findByName(String name) {
        return labelRepository.findByName(name)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.label.notfound",
                new Object[] { name }, LocaleContextHolder.getLocale())));
    }

    public Label findById(Long id) {
        return labelRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.label.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Label> findAll(Pageable pageable) {
        return labelRepository.findAll(pageable);
    }

    public LabelResponse fromEntity(Label label) {
        LabelResponse labelResponse = new LabelResponse();
        labelResponse.setId(label.getId());
        labelResponse.setName(label.getName());
        labelResponse.setDescription(label.getDescription());
        labelResponse.setCreatedAt(label.getCreatedAt());
        return labelResponse;
    }
}
