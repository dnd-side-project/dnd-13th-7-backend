package com.moyeoit.domain.user.infra;

import com.moyeoit.domain.user.domain.AuthProvider;
import com.moyeoit.domain.user.domain.User;
import com.moyeoit.domain.user.domain.repository.UserRepository;
import com.moyeoit.domain.user.infra.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Long save(User user) {
        return jpaUserRepository.save(user).getId();
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmailAndProvider(String email, AuthProvider provider) {
        return jpaUserRepository.findByEmailAndProvider(email, provider);
    }
}