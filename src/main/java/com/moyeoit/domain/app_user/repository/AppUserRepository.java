package com.moyeoit.domain.app_user.repository;

import com.moyeoit.domain.app_user.domain.AppUser;
import com.moyeoit.domain.app_user.domain.AuthProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT au FROM AppUser au LEFT JOIN FETCH au.job WHERE au.id = :userId")
    Optional<AppUser> findById(@Param("userId") Long userId);

    @Query("SELECT au FROM AppUser au WHERE au.email = :email AND au.provider = :provider")
    Optional<AppUser> findByEmailAndProvider(@Param("email") String email,
                                             @Param("provider") AuthProvider provider);

    @Query("SELECT au FROM AppUser au LEFT JOIN FETCH au.job WHERE au.id = :userId")
    Optional<AppUser> findByIdWithJob(@Param("userId") Long userId);

}