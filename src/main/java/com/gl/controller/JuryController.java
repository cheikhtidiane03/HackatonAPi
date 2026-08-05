package com.gl.controller;

import com.gl.dto.EvaluationRequest;
import com.gl.dto.ProjectDTO;
import com.gl.service.EvaluationService;
import com.gl.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jury")
@Tag(name = "Jury", description = "Consultation et notation des projets (réservé au jury)")
@PreAuthorize("hasRole('JURY')")
public class JuryController {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private ProjectService projectService;

    @Operation(summary = "Lister les projets à évaluer")
    @GetMapping("/projects")
    public List<ProjectDTO> projects() {
        return projectService.findAll();
    }

    @Operation(summary = "Noter un projet")
    @PostMapping("/evaluations")
    public ResponseEntity<?> evaluate(@Valid @RequestBody EvaluationRequest request, Authentication authentication) {
        evaluationService.evaluate(request, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
