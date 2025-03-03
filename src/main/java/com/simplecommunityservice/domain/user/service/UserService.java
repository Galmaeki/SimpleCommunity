package com.simplecommunityservice.domain.user.service;

import com.simplecommunityservice.domain.user.dto.RequestLogin;
import com.simplecommunityservice.domain.user.dto.RequestSignup;
import com.simplecommunityservice.domain.user.dto.ResponseDuplicate;
import com.simplecommunityservice.domain.user.dto.ResponseUser;
import com.simplecommunityservice.domain.user.entity.Users;
import com.simplecommunityservice.domain.user.repository.UserRepository;
import com.simplecommunityservice.exception.ApplicationException;
import com.simplecommunityservice.exception.ErrorCode;
import com.simplecommunityservice.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseDuplicate duplicateUserId(String userId) {
        return new ResponseDuplicate(userRepository.existsByUserId(userId));
    }

    @Transactional
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
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(login.password(), user.getPassword()))
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);

        return jwtTokenProvider.generateToken(user);
    }

    private ResponseUser toResponseUser(Users user) {
        return new ResponseUser(user.getId(), user.getUserId(), user.getEmail(), user.getNickname());
    }

    public ResponseUser getUserInfo(RequestLogin login) {
        return toResponseUser(userRepository.findByUserId(login.userid()).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND)
        ));
    }

    public ResponseUser getUserInfo(String userId) {
        return toResponseUser(userRepository.findByUserId(userId).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND)
        ));
    }

    public void changeEmail(String pathUserId, String loginUserId, String email) {
        Users targetUser = userRepository.findByUserId(pathUserId).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND)
        );

        if (targetUser.getUserId().equals(loginUserId))
            throw new ApplicationException(ErrorCode.UNAUTHORIZED_ACCESS);

        targetUser.setEmail(email);

        userRepository.save(targetUser);
    }

    public void changeNickname(String pathUserId, String loginUserId, String nickname) {
        Users targetUser = userRepository.findByUserId(pathUserId).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND)
        );

        if (targetUser.getUserId().equals(loginUserId))
            throw new ApplicationException(ErrorCode.UNAUTHORIZED_ACCESS);

        targetUser.setNickname(nickname);

        userRepository.save(targetUser);
    }

    public void changePassword(String pathUserId, String loginUserId, String password) {
        Users targetUser = userRepository.findByUserId(pathUserId).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND)
        );

        if (targetUser.getUserId().equals(loginUserId))
            throw new ApplicationException(ErrorCode.UNAUTHORIZED_ACCESS);

        targetUser.setPassword(passwordEncoder.encode(password));

        userRepository.save(targetUser);
    }
}
