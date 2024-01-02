package pl.matrasj.quiz.quizquestionanswer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestionAnswerWithCorrectInfoDto {
    private Long id;
    private String content;
    private boolean correct;
}
