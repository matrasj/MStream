package pl.matrasj.quiz.quizquestion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestionEntity, Long> {
    @Query(value = "SELECT * FROM quiz_question " +
            "WHERE (quiz_category_id IN :categoryIds) AND (active = true) " +
            "ORDER BY RANDOM() LIMIT :count", nativeQuery = true)
    List<QuizQuestionEntity> getRandomActiveQuestionsByCategories(
            @Param("categoryIds") List<Long> categoryIds,
            @Param("count") int count);

    @Query("SELECT qqe FROM QuizQuestionEntity qqe WHERE (qqe.id IN :ids)")
    List<QuizQuestionEntity> getBatchByIds(@Param("ids") Set<Long> ids);

    @Query("SELECT qqe FROM QuizQuestionEntity qqe WHERE (qqe.active = true) AND (qqe.quizCategory.id = :quizCategoryId)")
    Page<QuizQuestionEntity> findAllActiveByQuizCategoryId(@Param("quizCategoryId") Long quizCategoryId, Pageable pageable);
}
