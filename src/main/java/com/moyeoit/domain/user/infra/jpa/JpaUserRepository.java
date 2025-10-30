package com.moyeoit.domain.user.infra.jpa;

import com.moyeoit.domain.user.domain.AuthProvider;
import com.moyeoit.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id = :userId AND u.deleted = false")
    Optional<User> findById(@Param("userId") Long userId);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.provider = :provider")
    Optional<User> findByEmailAndProvider(@Param("email") String email,
                                          @Param("provider") AuthProvider provider);

}