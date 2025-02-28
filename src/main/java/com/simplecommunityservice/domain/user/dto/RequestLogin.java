package com.simplecommunityservice.domain.user.dto;

public record RequestLogin(
        String userid,
        String password
) {
}
