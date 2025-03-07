package com.simplecommunityservice.domain.post.entity;

import com.simplecommunityservice.domain.comment.entity.Comment;
import com.simplecommunityservice.domain.post.dto.RequestPostPost;
import com.simplecommunityservice.domain.user.entity.Users;
import com.simplecommunityservice.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public Post(RequestPostPost post, Users user) {
        this.title = post.title();
        this.content = post.content();
        this.user = user;
        this.comments = new ArrayList<>();
    }

    public void setPost(RequestPostPost post) {
        this.title = post.title();
        this.content = post.content();
    }
}
