package com.simplecommunityservice.domain.post.dto;

import com.simplecommunityservice.domain.comment.dto.ResponseCommentDetail;

import java.time.LocalDateTime;
import java.util.List;

public record ResponsePostDetail(
        String title,
        List<ResponseCommentDetail> comments,
        Integer like,
        String nickname,
        LocalDateTime lastModifiedAt
) {
}
