package com.gl.service;

import com.gl.dto.TeamCreateRequest;
import com.gl.dto.TeamDTO;
import com.gl.dto.TeamMemberDTO;
import com.gl.converter.TeamConverter;
import com.gl.exception.BadRequestException;
import com.gl.exception.ResourceNotFound;
import com.gl.model.Team;
import com.gl.model.TeamMember;
import com.gl.model.Utilisateur;
import com.gl.repository.TeamMemberRepository;
import com.gl.repository.TeamRepository;
import com.gl.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private Utilisateur getUser(String username) {
        return utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFound("Utilisateur introuvable : " + username));
    }

    @Transactional
    public TeamDTO createTeam(TeamCreateRequest request, String username) {
        Utilisateur user = getUser(username);
        if (teamMemberRepository.existsByUser(user)) {
            throw new BadRequestException("Vous êtes déjà membre d'une équipe");
        }
        Team team = new Team();
        team.setName(request.getName());
        team.setCreatedBy(user);
        team = teamRepository.save(team);

        TeamMember member = new TeamMember();
        member.setTeam(team);
        member.setUser(user);
        teamMemberRepository.save(member);

        return TeamConverter.toDTO(team);
    }

    @Transactional
    public TeamDTO joinTeam(Integer teamId, String username) {
        Utilisateur user = getUser(username);
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFound("Équipe introuvable : " + teamId));

        if (teamMemberRepository.existsByUser(user)) {
            throw new BadRequestException("Vous êtes déjà membre d'une équipe");
        }

        TeamMember member = new TeamMember();
        member.setTeam(team);
        member.setUser(user);
        teamMemberRepository.save(member);

        return TeamConverter.toDTO(team);
    }

    @Transactional
    public void leaveTeam(Integer teamId, String username) {
        Utilisateur user = getUser(username);
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFound("Équipe introuvable : " + teamId));

        TeamMember member = teamMemberRepository.findByUserAndTeam(user, team)
                .orElseThrow(() -> new BadRequestException("Vous n'êtes pas membre de cette équipe"));

        teamMemberRepository.delete(member);
    }

    public List<TeamDTO> findAll() {
        return teamRepository.findAll().stream()
                .map(TeamConverter::toDTO)
                .collect(Collectors.toList());
    }

    public List<TeamMemberDTO> findMembers(Integer teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFound("Équipe introuvable : " + teamId));
        return teamMemberRepository.findByTeam(team).stream()
                .map(m -> new TeamMemberDTO(m.getUser().getId(), m.getUser().getUsername()))
                .collect(Collectors.toList());
    }

    public Team getTeamOfUser(String username) {
        Utilisateur user = getUser(username);
        TeamMember member = teamMemberRepository.findByUser(user)
                .orElseThrow(() -> new BadRequestException("Vous n'appartenez à aucune équipe"));
        return member.getTeam();
    }
}
