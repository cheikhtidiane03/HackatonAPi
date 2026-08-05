package com.gl.controller;

import com.gl.dto.TeamCreateRequest;
import com.gl.dto.TeamDTO;
import com.gl.dto.TeamMemberDTO;
import com.gl.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@Tag(name = "Équipes", description = "Création et gestion des équipes")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Operation(summary = "Créer une équipe")
    @PostMapping
    public ResponseEntity<TeamDTO> create(@Valid @RequestBody TeamCreateRequest request, Authentication authentication) {
        return ResponseEntity.ok(teamService.createTeam(request, authentication.getName()));
    }

    @Operation(summary = "Rejoindre une équipe existante")
    @PostMapping("/{id}/join")
    public ResponseEntity<TeamDTO> join(@PathVariable("id") Integer id, Authentication authentication) {
        return ResponseEntity.ok(teamService.joinTeam(id, authentication.getName()));
    }

    @Operation(summary = "Quitter son équipe")
    @PostMapping("/{id}/leave")
    public ResponseEntity<?> leave(@PathVariable("id") Integer id, Authentication authentication) {
        teamService.leaveTeam(id, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Lister toutes les équipes")
    @GetMapping
    public List<TeamDTO> findAll() {
        return teamService.findAll();
    }

    @Operation(summary = "Voir les membres d'une équipe")
    @GetMapping("/{id}/members")
    public List<TeamMemberDTO> members(@PathVariable("id") Integer id) {
        return teamService.findMembers(id);
    }
}
