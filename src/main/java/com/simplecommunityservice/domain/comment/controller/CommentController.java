package com.simplecommunityservice.domain.comment.controller;

import com.simplecommunityservice.domain.comment.dto.ResponseComment;
import com.simplecommunityservice.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Void> createComment(@PathVariable Long postId, @RequestBody ResponseComment comment) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        commentService.createPost(postId, comment, user.getUsername());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId, @RequestBody ResponseComment comment) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        commentService.updateComment(commentId, comment, user.getUsername());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        commentService.deleteComment(commentId, user.getUsername());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
