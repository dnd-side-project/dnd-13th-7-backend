package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.review.domain.Question;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q JOIN FETCH q.questionElements qe WHERE q.id = :questionId "
            + "ORDER BY qe.sequence")
    Optional<Question> findByIdWithQuestionElements(@Param("questionId") Long questionId);

}
