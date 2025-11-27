package com.moyeoit.context.user.domain.repository;

import com.moyeoit.context.user.domain.AuthProvider;
import com.moyeoit.context.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    Long save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmailAndProvider(String email, AuthProvider provider);

    boolean existsByUserId(Long userId);
}