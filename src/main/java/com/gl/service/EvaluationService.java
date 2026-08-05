package com.gl.service;

import com.gl.dto.EvaluationRequest;
import com.gl.exception.ResourceNotFound;
import com.gl.model.Evaluation;
import com.gl.model.Project;
import com.gl.model.Utilisateur;
import com.gl.repository.EvaluationRepository;
import com.gl.repository.ProjectRepository;
import com.gl.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Transactional
    public void evaluate(EvaluationRequest request, String juryUsername) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFound("Projet introuvable : " + request.getProjectId()));

        Utilisateur jury = utilisateurRepository.findByUsername(juryUsername)
                .orElseThrow(() -> new ResourceNotFound("Utilisateur introuvable : " + juryUsername));

        Evaluation evaluation = evaluationRepository.findByProjectAndJury(project, jury)
                .orElseGet(Evaluation::new);

        evaluation.setProject(project);
        evaluation.setJury(jury);
        evaluation.setScore(request.getScore());
        evaluation.setComment(request.getComment());
        evaluationRepository.save(evaluation);
    }
}
