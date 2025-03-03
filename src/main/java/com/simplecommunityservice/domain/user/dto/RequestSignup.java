package com.simplecommunityservice.domain.user.dto;

public record RequestSignup(
        String userid,
        String password,
        String email,
        String nickname
) {
}
