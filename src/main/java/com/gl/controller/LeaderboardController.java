package com.gl.controller;

import com.gl.dto.LeaderboardEntry;
import com.gl.dto.PublishStatus;
import com.gl.service.AdminService;
import com.gl.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;
    @Autowired
    private AdminService adminService;

    @GetMapping
    public List<LeaderboardEntry> leaderboard(Authentication authentication) {
        if (!adminService.isResultsPublished() && !isAdmin(authentication)) {
            return Collections.emptyList();
        }
        return leaderboardService.getLeaderboard();
    }

    @GetMapping("/status")
    public PublishStatus status() {
        return new PublishStatus(adminService.isResultsPublished());
    }

    private boolean isAdmin(Authentication authentication) {
        if (authentication == null) return false;
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_ADMIN"));
    }
}