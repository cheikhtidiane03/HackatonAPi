package com.gl.service;

import com.gl.dto.AdminCreateUserRequest;
import com.gl.dto.UserAdminDTO;
import com.gl.exception.BadRequestException;
import com.gl.exception.ResourceNotFound;
import com.gl.model.*;
import com.gl.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private SettingsRepository settingsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserAdminDTO> listUsers() {
        return utilisateurRepository.findAll().stream()
                .map(u -> new UserAdminDTO(u.getId(), u.getUsername(), u.getRole().name()))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserAdminDTO createUser(AdminCreateUserRequest request) {
        if (utilisateurRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Ce nom d'utilisateur est déjà pris");
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setUsername(request.getUsername());
        utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        utilisateur.setRole(request.getRole());
        utilisateur = utilisateurRepository.save(utilisateur);
        return new UserAdminDTO(utilisateur.getId(), utilisateur.getUsername(), utilisateur.getRole().name());
    }

    @Transactional
    public void deleteUser(Integer id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Utilisateur introuvable : " + id));
        evaluationRepository.deleteByJury(utilisateur);
        teamMemberRepository.deleteByUser(utilisateur);
        utilisateurRepository.delete(utilisateur);
    }

    @Transactional
    public void deleteTeam(Integer id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Équipe introuvable : " + id));
        projectRepository.findByTeam(team).ifPresent(project -> {
            evaluationRepository.deleteByProject(project);
            projectRepository.delete(project);
        });
        teamMemberRepository.deleteByTeam(team);
        teamRepository.delete(team);
    }

    @Transactional
    public void deleteProject(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Projet introuvable : " + id));
        evaluationRepository.deleteByProject(project);
        projectRepository.delete(project);
    }

    public boolean isResultsPublished() {
        return getOrCreateSettings().isResultsPublished();
    }

    @Transactional
    public boolean togglePublish() {
        Settings settings = getOrCreateSettings();
        settings.setResultsPublished(!settings.isResultsPublished());
        settingsRepository.save(settings);
        return settings.isResultsPublished();
    }

    private Settings getOrCreateSettings() {
        return settingsRepository.findById(1)
                .orElseGet(() -> settingsRepository.save(new Settings(1, false)));
    }
}