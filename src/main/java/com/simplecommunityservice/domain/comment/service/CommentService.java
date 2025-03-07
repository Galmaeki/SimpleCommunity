package com.simplecommunityservice.domain.comment.service;

import com.simplecommunityservice.domain.comment.dto.ResponseComment;
import com.simplecommunityservice.domain.comment.entity.Comment;
import com.simplecommunityservice.domain.comment.entity.CommentLike;
import com.simplecommunityservice.domain.comment.repository.CommentLikeRepository;
import com.simplecommunityservice.domain.comment.repository.CommentRepository;
import com.simplecommunityservice.domain.post.entity.Post;
import com.simplecommunityservice.domain.post.repository.PostRepository;
import com.simplecommunityservice.domain.user.entity.Users;
import com.simplecommunityservice.domain.user.repository.UserRepository;
import com.simplecommunityservice.exception.ApplicationException;
import com.simplecommunityservice.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentLikeRepository commentLikeRepository;

    public void createPost(Long postId, ResponseComment requestComment, String username) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ApplicationException(ErrorCode.POST_NOT_FOUND)
        );

        Users user = userRepository.findByUserId(username).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND)
        );

        Comment comment = commentRepository.save(new Comment(requestComment, post, user));

        post.addComment(comment);
    }

    public void updateComment(Long commentId, ResponseComment requestComment, String username) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND)
        );

        if (!comment.getUser().getUserId().equals(username))
            throw new ApplicationException(ErrorCode.UNAUTHORIZED_ACCESS);

        comment.updateComment(requestComment);

        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND)
        );

        if (!comment.getUser().getUserId().equals(username))
            throw new ApplicationException(ErrorCode.UNAUTHORIZED_ACCESS);

        commentRepository.delete(comment);
    }

    public void likeComment(Long commentId, String username) {
        Users users = userRepository.findByUserId(username).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND)
        );

        if (commentLikeRepository.existsByCommentAndUser(comment, users))
            throw new ApplicationException(ErrorCode.DUPLICATED);

        commentLikeRepository.save(new CommentLike(users, comment));

    }

    public void cancelCommentLike(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND)
        );

        Users user = userRepository.findByUserId(username).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND)
        );

        CommentLike commentLike = commentLikeRepository.findByCommentAndUser(comment, user).orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND)
        );

        commentLikeRepository.delete(commentLike);
    }
}
