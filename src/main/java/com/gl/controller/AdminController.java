package com.gl.controller;

import com.gl.dto.*;
import com.gl.service.AdminService;
import com.gl.service.ProjectService;
import com.gl.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private ProjectService projectService;

    @GetMapping("/users")
    public List<UserAdminDTO> users() {
        return adminService.listUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<UserAdminDTO> createUser(@Valid @RequestBody AdminCreateUserRequest request) {
        return ResponseEntity.ok(adminService.createUser(request));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/teams")
    public List<TeamDTO> teams() {
        return teamService.findAll();
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable Integer id) {
        adminService.deleteTeam(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/projects")
    public List<ProjectDTO> projects() {
        return projectService.findAll();
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Integer id) {
        adminService.deleteProject(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/leaderboard/publish")
    public PublishStatus togglePublish() {
        return new PublishStatus(adminService.togglePublish());
    }
}