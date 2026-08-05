package com.gl.repository;

import com.gl.model.Project;
import com.gl.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<Project> findByTeam(Team team);
}
