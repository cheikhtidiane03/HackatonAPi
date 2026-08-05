package com.gl.converter;

import com.gl.dto.ProjectDTO;
import com.gl.model.Project;

public class ProjectConverter {

    public static ProjectDTO toDTO(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getGithubLink(),
                project.getTeam().getId(),
                project.getTeam().getName()
        );
    }

    public static Project toEntity(ProjectDTO dto) {
        Project project = new Project();
        project.setId(dto.getId());
        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setGithubLink(dto.getGithubLink());
        return project;
    }
}
