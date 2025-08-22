package com.github.andreyjodar.backend.features.labels.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/labels")
public class LabelController {
    
    @Autowired
    LabelService labelService;

    @PostMapping
    public ResponseEntity<LabelResponse> create(@Valid @RequestBody LabelRequest labelRequest) {
        Label label = new Label(labelRequest.getName(), labelRequest.getDescription());
        label = labelService.create(label);
        return ResponseEntity.ok(labelService.fromEntity(label));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabelResponse> update(@PathVariable("id") Long id, @Valid @RequestBody LabelRequest labelRequest) {
        Label label = new Label(labelRequest.getName(), labelRequest.getDescription());
        label.setId(id);
        label = labelService.update(label);
        return ResponseEntity.ok(labelService.fromEntity(label));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        labelService.delete(id);
        return ResponseEntity.ok("Label was deleted");
    }

    @GetMapping
    public ResponseEntity<List<LabelResponse>> findAll() {
        List<Label> labels = labelService.findAll();
        List<LabelResponse> labelsResponse = labels.stream()
            .map(labelService::fromEntity).toList();
        return ResponseEntity.ok(labelsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabelResponse> findById(@PathVariable("id") Long id) {
        Label label = labelService.findById(id);
        LabelResponse labelResponse = labelService.fromEntity(label); 
        return ResponseEntity.ok(labelResponse);
    }

}
