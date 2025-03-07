package com.simplecommunityservice.domain.comment.repository;

import com.simplecommunityservice.domain.comment.entity.Comment;
import com.simplecommunityservice.domain.comment.entity.CommentLike;
import com.simplecommunityservice.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentAndUser(Comment comment, Users user);

    Boolean existsByCommentAndUser(Comment comment, Users users);
}
