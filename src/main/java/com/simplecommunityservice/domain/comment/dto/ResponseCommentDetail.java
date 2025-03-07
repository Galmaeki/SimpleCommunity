package com.simplecommunityservice.domain.comment.dto;

import java.time.LocalDateTime;

public record ResponseCommentDetail(
        String comment,
        String nickname,
        LocalDateTime lastModifiedAt
) {
}
