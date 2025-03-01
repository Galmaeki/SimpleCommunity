package com.simplecommunityservice.domain.user.controller;

import com.simplecommunityservice.domain.user.dto.*;
import com.simplecommunityservice.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<ResponseDuplicate> duplicateUserId(@RequestParam String userId) {
        return ResponseEntity.ok(userService.duplicateUserId(userId));
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseUser> signup(@RequestBody RequestSignup signup) {
        return ResponseEntity.ok(userService.signup(signup));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseUser> login(@RequestBody RequestLogin login, HttpServletResponse response) {
        String token = userService.login(login);

        response.addHeader(HttpHeaders.AUTHORIZATION, token);
        return ResponseEntity.ok(userService.getUserInfo(login));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseUser> userInfo(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

    @PatchMapping("/{userid}/email")
    public ResponseEntity<Void> changeEmail(
            @PathVariable String userid, @RequestBody RequestChangeEmail email
    ) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.changeEmail(userid, user.getUsername(), email.email());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{userid}/nickname")
    public ResponseEntity<Void> changeNickname(
            @PathVariable String userid, @RequestBody RequestChangeNickname nickname
    ) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.changeNickname(userid, user.getUsername(), nickname.nickname());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{userid}/password")
    public ResponseEntity<Void> changeNickname(
            @PathVariable String userid, @RequestBody RequestChangePassword password
    ) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.changePassword(userid, user.getUsername(), password.password());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
