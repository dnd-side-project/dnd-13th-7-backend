package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.review.domain.QuestionElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionElementRepository extends JpaRepository<QuestionElement, Long> {
}
