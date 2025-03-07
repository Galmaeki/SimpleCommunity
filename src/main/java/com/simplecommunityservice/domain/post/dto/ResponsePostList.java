package com.simplecommunityservice.domain.post.dto;

import java.time.LocalDateTime;

public record ResponsePostList(
        String title,
        Integer comments,
        Integer like,
        String nickname,
        LocalDateTime lastModifiedAt
) {
}
