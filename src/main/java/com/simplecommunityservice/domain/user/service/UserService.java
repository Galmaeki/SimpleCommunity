package com.simplecommunityservice.domain.user.service;

import com.simplecommunityservice.domain.user.dto.RequestLogin;
import com.simplecommunityservice.domain.user.dto.RequestSignup;
import com.simplecommunityservice.domain.user.dto.ResponseDuplicate;
import com.simplecommunityservice.domain.user.dto.ResponseUser;
import com.simplecommunityservice.domain.user.entity.Users;
import com.simplecommunityservice.domain.user.repository.UserRepository;
import com.simplecommunityservice.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseDuplicate duplicateUserId(String userId) {
        return new ResponseDuplicate(userRepository.existsByUserId(userId));
    }

    public ResponseUser signup(RequestSignup signup) {
        return toResponseUser(userRepository.save(
                Users.builder()
                        .userId(signup.userid())
                        .email(signup.email())
                        .nickname(signup.nickname())
                        .password(passwordEncoder.encode(signup.password()))
                        .build()
        ));
    }

    public String login(RequestLogin login) {
        Users user = userRepository.findByUserId(login.userid())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));

        if (!passwordEncoder.matches(login.password(), user.getPassword()))
            throw new RuntimeException("유저를 찾을 수 없습니다");

        return jwtTokenProvider.generateToken(user);
    }

    private ResponseUser toResponseUser(Users user) {
        return new ResponseUser(user.getId(), user.getUserId(), user.getEmail(), user.getNickname());
    }

    public ResponseUser getUserInfo(RequestLogin login) {
        return toResponseUser(userRepository.findByUserId(login.userid()).orElseThrow());
    }
}
