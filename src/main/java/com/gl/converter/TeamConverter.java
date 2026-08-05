package com.gl.converter;

import com.gl.dto.TeamDTO;
import com.gl.model.Team;

public class TeamConverter {

    public static TeamDTO toDTO(Team team) {
        return new TeamDTO(
                team.getId(),
                team.getName(),
                team.getCreatedBy() != null ? team.getCreatedBy().getUsername() : null
        );
    }
}
