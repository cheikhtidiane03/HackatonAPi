package com.gl.controller;

import com.gl.dto.LoginDetails;
import com.gl.dto.LoginRequest;
import com.gl.dto.RegisterRequest;
import com.gl.model.Utilisateur;
import com.gl.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentification", description = "Inscription et connexion des utilisateurs")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Créer un compte (participant, jury ou admin)")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        Utilisateur utilisateur = authService.register(request);
        return ResponseEntity.ok().body(utilisateur.getUsername() + " créé avec succès");
    }

    @Operation(summary = "Se connecter et récupérer un token JWT")
    @PostMapping("/login")
    public ResponseEntity<LoginDetails> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
