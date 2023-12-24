package pl.matrasj.quiz.quizcategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCategoryRepository extends JpaRepository<QuizCategoryEntity, Long> {
}
