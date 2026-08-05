package com.gl.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamCreateRequest {
    @NotBlank
    private String name;
}
