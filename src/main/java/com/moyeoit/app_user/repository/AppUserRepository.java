package com.moyeoit.app_user.repository;

import com.moyeoit.app_user.domain.AppUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT au FROM AppUser au WHERE au.email = :email")
    Optional<AppUser> findByEmail(@Param("email") String email);

}