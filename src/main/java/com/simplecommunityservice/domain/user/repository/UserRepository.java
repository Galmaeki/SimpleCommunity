package com.simplecommunityservice.domain.user.repository;

import com.simplecommunityservice.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    boolean existsByUserId(String string);

    Optional<Users> findByUserId(String userId);
}
