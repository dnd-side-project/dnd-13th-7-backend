package com.moyeoit.context.user.infra;

import com.moyeoit.context.user.domain.AuthProvider;
import com.moyeoit.context.user.domain.User;
import com.moyeoit.context.user.domain.repository.UserRepository;
import com.moyeoit.context.user.infra.jpa.JpaUserRepository;
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
    public boolean existsByNickname(String nickname) {
        return jpaUserRepository.existsByNickname(nickname);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmailAndProvider(String email, AuthProvider provider) {
        return jpaUserRepository.findByEmailAndProvider(email, provider);
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return jpaUserRepository.existsById(userId);
    }
}