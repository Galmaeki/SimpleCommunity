package com.simplecommunityservice.security;

import com.simplecommunityservice.domain.user.entity.Users;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final Long expiredTimeMills;
    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(@Value("${token.secret}") String secret, @Value("${token.ext}") Long expiredTimeMills,
                            UserDetailsService userDetailsService
    ) {
        this.key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
        this.expiredTimeMills = expiredTimeMills;
        this.userDetailsService = userDetailsService;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserId(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload()
                .getSubject();
    }

    public String generateToken(Users user) {
        return Jwts.builder()
                .subject(user.getUserId())
                .expiration(new Date(System.currentTimeMillis() + expiredTimeMills))
                .signWith(key)
                .compact();
    }
}