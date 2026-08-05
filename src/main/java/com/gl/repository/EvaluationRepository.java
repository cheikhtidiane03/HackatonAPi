package com.gl.repository;

import com.gl.model.Evaluation;
import com.gl.model.Project;
import com.gl.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
    List<Evaluation> findByProject(Project project);
    void deleteByProject(Project project);
    void deleteByJury(Utilisateur jury);
    Optional<Evaluation> findByProjectAndJury(Project project, Utilisateur jury);
}