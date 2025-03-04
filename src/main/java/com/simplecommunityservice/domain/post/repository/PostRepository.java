package com.simplecommunityservice.domain.post.repository;

import com.simplecommunityservice.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
