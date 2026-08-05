package com.gl.service;

import com.gl.converter.ProjectConverter;
import com.gl.dto.ProjectDTO;
import com.gl.exception.BadRequestException;
import com.gl.exception.ResourceNotFound;
import com.gl.model.Project;
import com.gl.model.Team;
import com.gl.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamService teamService;

    @Transactional
    public ProjectDTO submit(ProjectDTO dto, String username) {
        Team team = teamService.getTeamOfUser(username);

        if (projectRepository.findByTeam(team).isPresent()) {
            throw new BadRequestException("Votre équipe a déjà soumis un projet");
        }

        Project project = ProjectConverter.toEntity(dto);
        project.setId(null);
        project.setTeam(team);
        project = projectRepository.save(project);

        return ProjectConverter.toDTO(project);
    }

    @Transactional
    public ProjectDTO update(Integer id, ProjectDTO dto, String username) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Projet introuvable : " + id));

        Team team = teamService.getTeamOfUser(username);
        if (!project.getTeam().getId().equals(team.getId())) {
            throw new BadRequestException("Vous ne pouvez modifier que le projet de votre équipe");
        }

        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setGithubLink(dto.getGithubLink());
        project = projectRepository.save(project);

        return ProjectConverter.toDTO(project);
    }

    public List<ProjectDTO> findAll() {
        return projectRepository.findAll().stream()
                .map(ProjectConverter::toDTO)
                .collect(Collectors.toList());
    }

    public ProjectDTO findById(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Projet introuvable : " + id));
        return ProjectConverter.toDTO(project);
    }
}
