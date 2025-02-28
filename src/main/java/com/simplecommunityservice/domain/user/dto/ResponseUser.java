package com.simplecommunityservice.domain.user.dto;

public record ResponseUser(
        Long id,
        String userId,
        String email,
        String nickname
) {
}
