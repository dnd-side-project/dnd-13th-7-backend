package com.moyeoit.domain.user.domain.repository;

import com.moyeoit.domain.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

}