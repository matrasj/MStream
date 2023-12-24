package pl.matrasj.quiz.quizquestionanswer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "quiz_question_answer")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizQuestionAnswerEntity {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "content")
    private String content;
    @Column(name = "active")
    private boolean active;
    @Column(name = "quiz_question_id")
    private Long quizQuestionId;
    @Column(name = "correct")
    private boolean correct;
}
