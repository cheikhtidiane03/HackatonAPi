package com.gl.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EvaluationRequest {
    @NotNull
    private Integer projectId;

    @NotNull
    private Double score;

    private String comment;
}
