package com.gl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team_member", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "team_id"}))
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Utilisateur user;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
