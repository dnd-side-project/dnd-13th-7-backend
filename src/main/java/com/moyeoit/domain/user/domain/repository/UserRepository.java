package com.moyeoit.domain.user.domain.repository;

import com.moyeoit.domain.user.domain.AuthProvider;
import com.moyeoit.domain.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    Long save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmailAndProvider(String email, AuthProvider provider);
}