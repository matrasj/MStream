package pl.matrasj.quiz.quizquestion;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestionEntity, Long> {
    @Query(value = "SELECT * FROM quiz_question " +
            "WHERE (quiz_category_id IN :categoryIds) AND (active = true) " +
            "ORDER BY RANDOM() LIMIT :count", nativeQuery = true)
    List<QuizQuestionEntity> getRandomActiveQuestionsByCategories(
            @Param("categoryIds") List<Long> categoryIds,
            @Param("count") int count);
}
