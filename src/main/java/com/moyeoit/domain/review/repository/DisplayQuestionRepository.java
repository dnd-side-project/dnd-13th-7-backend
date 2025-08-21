package com.moyeoit.domain.review.repository;

import com.moyeoit.domain.review.domain.DisplayQuestion;
import com.moyeoit.domain.review.domain.ReviewCategory;
import com.moyeoit.domain.review.domain.ReviewType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DisplayQuestionRepository extends JpaRepository<DisplayQuestion, Long> {

    @Query("SELECT d FROM DisplayQuestion d "
            + "LEFT JOIN FETCH d.question q "
            + "LEFT JOIN FETCH q.questionElements qe "
            + "WHERE d.reviewType = :type AND "
            + "d.reviewCategory = :category "
            + "ORDER BY d.sort")
    List<DisplayQuestion> findByTypeAndCategory(@Param("type") ReviewType type,
                                                @Param("category") ReviewCategory category);

}
