package pl.matrasj.quiz.quizquestion.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSolvedPayloadResponse {
    private int totalQuestionsCount;
    private int totalCorrectAnswersCount;
    private String resultPercent;
    private Map<Long, QuizQuestionAnswerInfoResultPayload> answersInfo;
}
