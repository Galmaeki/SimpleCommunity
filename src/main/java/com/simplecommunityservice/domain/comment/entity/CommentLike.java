package com.simplecommunityservice.domain.comment.entity;

import com.simplecommunityservice.util.LikeEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("COMMENT")
public class CommentLike extends LikeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;
}
