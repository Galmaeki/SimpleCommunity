package com.simplecommunityservice.domain.post.entity;

import com.simplecommunityservice.util.LikeEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("POST")
public class PostLike extends LikeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
