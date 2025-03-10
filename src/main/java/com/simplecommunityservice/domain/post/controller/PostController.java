package com.simplecommunityservice.domain.post.controller;

import com.simplecommunityservice.domain.post.dto.RequestPostPost;
import com.simplecommunityservice.domain.post.dto.ResponsePostDetail;
import com.simplecommunityservice.domain.post.dto.ResponsePostId;
import com.simplecommunityservice.domain.post.dto.ResponsePostList;
import com.simplecommunityservice.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @PostMapping("/{postId}/likes")
    public ResponseEntity<Void> likePost(@PathVariable Long postId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        postService.likePost(postId, user.getUsername());

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<Void> cancelLike(@PathVariable Long postId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        postService.cancelLike(postId, user.getUsername());

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<Page<ResponsePostList>> postList(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(postService.postList(pageable));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponsePostDetail> postDetail(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.postDetail(postId));
    }
}
