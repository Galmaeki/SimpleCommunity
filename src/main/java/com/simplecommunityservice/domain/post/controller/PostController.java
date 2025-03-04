package com.simplecommunityservice.domain.post.controller;

import com.simplecommunityservice.domain.post.dto.RequestPostPost;
import com.simplecommunityservice.domain.post.dto.ResponsePostId;
import com.simplecommunityservice.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ResponsePostId> postPost(@RequestBody RequestPostPost post) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(postService.postPost(post, user.getUsername()));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, @RequestBody RequestPostPost post) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        postService.updatePost(postId, post, user.getUsername());

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        postService.deletePost(postId, user.getUsername());

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
