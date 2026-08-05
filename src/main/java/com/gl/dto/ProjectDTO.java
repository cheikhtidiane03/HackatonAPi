package com.gl.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private Integer id;

    @NotBlank
    private String title;

    private String description;

    private String githubLink;

    private Integer teamId;

    private String teamName;
}
