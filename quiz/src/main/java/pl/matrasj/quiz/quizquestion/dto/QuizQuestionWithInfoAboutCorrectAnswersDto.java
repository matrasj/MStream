package pl.matrasj.quiz.quizquestion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.matrasj.quiz.quizquestionanswer.dto.QuizQuestionAnswerWithCorrectInfoDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestionWithInfoAboutCorrectAnswersDto {
    private Long id;
    private String content;
    private List<QuizQuestionAnswerWithCorrectInfoDto> answers;
}
