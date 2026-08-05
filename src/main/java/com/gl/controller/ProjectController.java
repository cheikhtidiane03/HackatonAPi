package com.gl.controller;

import com.gl.dto.ProjectDTO;
import com.gl.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@Tag(name = "Projets", description = "Soumission et consultation des projets")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Operation(summary = "Soumettre le projet de son équipe")
    @PostMapping
    public ResponseEntity<ProjectDTO> submit(@Valid @RequestBody ProjectDTO dto, Authentication authentication) {
        return ResponseEntity.ok(projectService.submit(dto, authentication.getName()));
    }

    @Operation(summary = "Modifier le projet de son équipe")
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody ProjectDTO dto, Authentication authentication) {
        return ResponseEntity.ok(projectService.update(id, dto, authentication.getName()));
    }

    @Operation(summary = "Lister tous les projets soumis")
    @GetMapping
    public List<ProjectDTO> findAll() {
        return projectService.findAll();
    }

    @Operation(summary = "Consulter un projet")
    @GetMapping("/{id}")
    public ProjectDTO findById(@PathVariable("id") Integer id) {
        return projectService.findById(id);
    }
}
