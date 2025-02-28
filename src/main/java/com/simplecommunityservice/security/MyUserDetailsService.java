package com.simplecommunityservice.security;

import com.simplecommunityservice.domain.user.entity.Users;
import com.simplecommunityservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Users user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다"));

        return User.builder()
                .username(user.getUserId())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
