package pl.matrasj.quiz.quizquestionanswer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestionAnswerDto {
    private Long id;
    private String content;
    private boolean correct;
}
