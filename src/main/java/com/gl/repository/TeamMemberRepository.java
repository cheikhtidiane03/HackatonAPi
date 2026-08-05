package com.gl.repository;

import com.gl.model.Team;
import com.gl.model.TeamMember;
import com.gl.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Integer> {
    List<TeamMember> findByTeam(Team team);
    Optional<TeamMember> findByUserAndTeam(Utilisateur user, Team team);
    Optional<TeamMember> findByUser(Utilisateur user);
    boolean existsByUser(Utilisateur user);
    void deleteByTeam(com.gl.model.Team team);
    void deleteByUser(com.gl.model.Utilisateur user);
}
