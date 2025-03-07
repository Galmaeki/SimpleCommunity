package com.simplecommunityservice.domain.post.service;

import com.simplecommunityservice.domain.post.dto.RequestPostPost;
import com.simplecommunityservice.domain.post.dto.ResponsePostId;
import com.simplecommunityservice.domain.post.entity.Post;
import com.simplecommunityservice.domain.post.entity.PostLike;
import com.simplecommunityservice.domain.post.repository.PostLikeRepository;
import com.simplecommunityservice.domain.post.repository.PostRepository;
import com.simplecommunityservice.domain.user.entity.Users;
import com.simplecommunityservice.domain.user.repository.UserRepository;
import com.simplecommunityservice.exception.ApplicationException;
import com.simplecommunityservice.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    public ResponsePostId postPost(RequestPostPost post, String user) {
        Users users = userRepository.findByUserId(user).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND)
        );

        return new ResponsePostId(
                postRepository.save(new Post(post, users))
                        .getId()
        );
    }

    public void updatePost(Long postId, RequestPostPost requestPost, String username) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ApplicationException(ErrorCode.POST_NOT_FOUND)
        );

        if (!post.getUser().getUserId().equals(username))
            throw new ApplicationException(ErrorCode.UNAUTHORIZED_ACCESS);

        post.setPost(requestPost);

        postRepository.save(post);
    }

    public void deletePost(Long postId, String username) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ApplicationException(ErrorCode.POST_NOT_FOUND)
        );

        if (!post.getUser().getUserId().equals(username))
            throw new ApplicationException(ErrorCode.UNAUTHORIZED_ACCESS);

        postRepository.delete(post);
    }

    public void likePost(Long postId, String username) {
        Users users = userRepository.findByUserId(username).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND)
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ApplicationException(ErrorCode.POST_NOT_FOUND)
        );

        if (postLikeRepository.existsByPostAndUser(post, users))
            throw new ApplicationException(ErrorCode.DUPLICATED);

        postLikeRepository.save(new PostLike(users, post));
    }

    public void cancelLike(Long postId, String username) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ApplicationException(ErrorCode.POST_NOT_FOUND)
        );

        Users user = userRepository.findByUserId(username).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND)
        );

        PostLike postLike = postLikeRepository.findByPostAndUser(post, user).orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND)
        );

        postLikeRepository.delete(postLike);
    }
}
