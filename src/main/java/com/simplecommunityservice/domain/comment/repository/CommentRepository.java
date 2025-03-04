package com.simplecommunityservice.domain.comment.repository;

import com.simplecommunityservice.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
