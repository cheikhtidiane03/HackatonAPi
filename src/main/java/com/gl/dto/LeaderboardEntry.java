package com.gl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaderboardEntry {
    private Integer teamId;
    private String teamName;
    private String projectTitle;
    private double averageScore;
    private int rank;
}
