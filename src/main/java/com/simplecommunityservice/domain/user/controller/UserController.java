package com.simplecommunityservice.domain.user.controller;

import com.simplecommunityservice.domain.user.dto.RequestLogin;
import com.simplecommunityservice.domain.user.dto.RequestSignup;
import com.simplecommunityservice.domain.user.dto.ResponseDuplicate;
import com.simplecommunityservice.domain.user.dto.ResponseUser;
import com.simplecommunityservice.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
}
