package com.moyeoit.domain.user.repository;

import com.moyeoit.domain.user.domain.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    /**
     * AppUser.id 통해 Term 정보를 조회합니다.
     *
     * @param userId AppUser ID
     * @return Term(Optional)
     */
    @Query("SELECT t FROM Term t WHERE t.user.id = :userId")
    Optional<Term> findByUserId(@Param("userId") Long userId);
}
