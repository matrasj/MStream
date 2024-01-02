package pl.matrasj.quiz.quizquestion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.matrasj.quiz.quizquestionanswer.dto.QuizQuestionAnswerDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestionDto {
    private Long id;
    private String content;
    private List<QuizQuestionAnswerDto> answers;
}
