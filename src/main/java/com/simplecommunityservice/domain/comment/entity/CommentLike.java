package com.simplecommunityservice.domain.comment.entity;

import com.simplecommunityservice.domain.user.entity.Users;
import com.simplecommunityservice.util.LikeEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("COMMENT")
@NoArgsConstructor
public class CommentLike extends LikeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    public CommentLike(Users user, Comment comment) {
        super(user);
        this.comment = comment;
    }
}
