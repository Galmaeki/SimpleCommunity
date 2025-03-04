package com.simplecommunityservice.domain.comment.entity;

import com.simplecommunityservice.domain.comment.dto.ResponseComment;
import com.simplecommunityservice.domain.post.entity.Post;
import com.simplecommunityservice.domain.user.entity.Users;
import com.simplecommunityservice.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    public Comment(ResponseComment comment, Post post, Users user) {
        this.comment = comment.comment();
        this.post = post;
        this.user = user;
    }

    public void updateComment(ResponseComment comment) {
        this.comment = comment.comment();
    }
}
