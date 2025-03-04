package com.simplecommunityservice.domain.post.repository;

import com.simplecommunityservice.domain.post.entity.Post;
import com.simplecommunityservice.domain.post.entity.PostLike;
import com.simplecommunityservice.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostAndUser(Post post, Users user);

    Boolean existsByPostAndUser(Post post, Users user);
}
