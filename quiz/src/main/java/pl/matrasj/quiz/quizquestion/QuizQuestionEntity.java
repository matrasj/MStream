package pl.matrasj.quiz.quizquestion;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import pl.matrasj.quiz.quizquestionanswer.QuizQuestionAnswerEntity;
import pl.matrasj.quiz.quizcategory.QuizCategoryEntity;

import java.util.List;

@Table(name = "quiz_question")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizQuestionEntity {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "content")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_category_id")
    private QuizCategoryEntity quizCategory;
    @Column(name = "active")
    private boolean active;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_question_id")
    @Where(clause = "active = true")
    private List<QuizQuestionAnswerEntity> answers;
}
