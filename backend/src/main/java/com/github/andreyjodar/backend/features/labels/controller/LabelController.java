package com.github.andreyjodar.backend.features.labels.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.features.labels.model.Label;
import com.github.andreyjodar.backend.features.labels.model.LabelRequest;
import com.github.andreyjodar.backend.features.labels.model.LabelResponse;
import com.github.andreyjodar.backend.features.labels.service.LabelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/labels")
public class LabelController {
    
    @Autowired
    LabelService labelService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LabelResponse> create(
            @Valid @RequestBody LabelRequest labelRequest) {

        Label label = new Label(labelRequest.getName(), labelRequest.getDescription());
        label = labelService.create(label);
        return ResponseEntity.ok(LabelResponse.fromEntity(label));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LabelResponse> update(
            @PathVariable("id") Long id, 
            @Valid @RequestBody LabelRequest labelRequest) {

        Label label = new Label(labelRequest.getName(), labelRequest.getDescription());
        label.setId(id);
        label = labelService.update(label);
        return ResponseEntity.ok(LabelResponse.fromEntity(label));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(
            @PathVariable("id") Long id) {

        labelService.delete(id);
        return ResponseEntity.ok("Label with id:" + id + " deleted succsessfully");
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Page<LabelResponse>> findAll(
            Pageable pageable) {

        Page<Label> labels = labelService.findAll(pageable);
        Page<LabelResponse> labelsResponse = labels.map(LabelResponse::fromEntity);
        return ResponseEntity.ok(labelsResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<LabelResponse> findById(
            @PathVariable("id") Long id) {
                
        Label label = labelService.findById(id);
        LabelResponse labelResponse = LabelResponse.fromEntity(label); 
        return ResponseEntity.ok(labelResponse);
    }

}
