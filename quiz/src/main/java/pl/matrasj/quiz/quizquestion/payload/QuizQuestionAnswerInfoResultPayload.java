package pl.matrasj.quiz.quizquestion.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestionAnswerInfoResultPayload {
    private Set<Long> correctAnswerIds;
    private Set<Long> userAnswerIds;
}