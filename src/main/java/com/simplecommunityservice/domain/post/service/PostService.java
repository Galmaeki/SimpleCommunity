package com.simplecommunityservice.domain.post.service;

import com.simplecommunityservice.domain.post.dto.RequestPostPost;
import com.simplecommunityservice.domain.post.dto.ResponsePostId;
import com.simplecommunityservice.domain.post.entity.Post;
import com.simplecommunityservice.domain.post.repository.PostRepository;
import com.simplecommunityservice.domain.user.entity.Users;
import com.simplecommunityservice.domain.user.repository.UserRepository;
import com.simplecommunityservice.exception.ApplicationException;
import com.simplecommunityservice.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

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

        if(!post.getUser().getUserId().equals(username))
            throw new ApplicationException(ErrorCode.UNAUTHORIZED_ACCESS);

        post.setPost(requestPost);

        postRepository.save(post);
    }

    public void deletePost(Long postId, String username) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ApplicationException(ErrorCode.POST_NOT_FOUND)
        );

        if(!post.getUser().getUserId().equals(username))
            throw new ApplicationException(ErrorCode.UNAUTHORIZED_ACCESS);

        postRepository.delete(post);
    }
}
