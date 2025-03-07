package com.simplecommunityservice.domain.post.entity;

import com.simplecommunityservice.domain.user.entity.Users;
import com.simplecommunityservice.util.LikeEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("POST")
@NoArgsConstructor
public class PostLike extends LikeEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public PostLike(Users user, Post post) {
        super(user);
        this.post = post;
    }
}
