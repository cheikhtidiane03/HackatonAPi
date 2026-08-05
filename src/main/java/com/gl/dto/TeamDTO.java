package com.gl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private Integer id;
    private String name;
    private String createdBy;
}
