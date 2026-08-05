package com.gl.config;

import com.gl.model.Role;
import com.gl.model.Utilisateur;
import com.gl.repository.UtilisateurRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrap {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createDefaultAdminIfNeeded() {
        if (utilisateurRepository.existsByRole(Role.ROLE_ADMIN)) {
            return;
        }
        Utilisateur admin = new Utilisateur();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ROLE_ADMIN);
        utilisateurRepository.save(admin);
        System.out.println("Compte admin par défaut créé : admin / admin123");
    }
}