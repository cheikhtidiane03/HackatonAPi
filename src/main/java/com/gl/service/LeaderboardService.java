package com.gl.service;

import com.gl.dto.LeaderboardEntry;
import com.gl.model.Evaluation;
import com.gl.model.Project;
import com.gl.repository.EvaluationRepository;
import com.gl.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    public List<LeaderboardEntry> getLeaderboard() {
        List<Project> projects = projectRepository.findAll();

        List<LeaderboardEntry> entries = projects.stream()
                .map(project -> {
                    List<Evaluation> evaluations = evaluationRepository.findByProject(project);
                    double average = evaluations.stream()
                            .mapToDouble(Evaluation::getScore)
                            .average()
                            .orElse(0.0);
                    return new LeaderboardEntry(
                            project.getTeam().getId(),
                            project.getTeam().getName(),
                            project.getTitle(),
                            average,
                            0
                    );
                })
                .sorted(Comparator.comparingDouble(LeaderboardEntry::getAverageScore).reversed())
                .collect(Collectors.toList());

        for (int i = 0; i < entries.size(); i++) {
            entries.get(i).setRank(i + 1);
        }

        return entries;
    }
}
