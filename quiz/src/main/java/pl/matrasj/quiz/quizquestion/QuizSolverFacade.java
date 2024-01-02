package pl.matrasj.quiz.quizquestion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import pl.matrasj.quiz.quizquestion.payload.QuizQuestionAnswerInfoResultPayload;
import pl.matrasj.quiz.quizquestion.payload.QuizSolvedPayloadRequest;
import pl.matrasj.quiz.quizquestion.payload.QuizSolvedPayloadResponse;
import pl.matrasj.quiz.quizquestionanswer.QuizQuestionAnswerEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizSolverFacade {
    QuizQuestionRepository quizQuestionRepository;
    public QuizSolvedPayloadResponse solveQuiz(QuizSolvedPayloadRequest request) {
        Map<Long, QuizQuestionAnswerInfoResultPayload> answersInfo = Maps.newLinkedHashMap();
        int totalCorrectAnswersCount = 0;

        Map<Long, List<Long>> solutions = request.getSolutions();
        List<QuizQuestionEntity> questions = quizQuestionRepository.getBatchByIds(solutions.keySet());
        for (QuizQuestionEntity quizQuestion: questions) {
            List<QuizQuestionAnswerEntity> answers = quizQuestion.getAnswers();

            Set<Long> userAnswersIds = new HashSet<>(solutions.get(quizQuestion.getId()));
            Set<Long> correctAnswerIds = answers
                    .stream()
                    .filter(QuizQuestionAnswerEntity::isCorrect)
                    .map(QuizQuestionAnswerEntity::getId)
                    .collect(Collectors.toSet());

            answersInfo.put(quizQuestion.getId(), QuizQuestionAnswerInfoResultPayload
                    .builder()
                    .userAnswerIds(userAnswersIds)
                    .correctAnswerIds(correctAnswerIds)
                    .build());
            if (correctAnswerIds.equals(userAnswersIds)) {
                totalCorrectAnswersCount++;
            }
        }

        return QuizSolvedPayloadResponse.builder()
                .totalQuestionsCount(questions.size())
                .totalCorrectAnswersCount(totalCorrectAnswersCount)
                .resultPercent(calculatePercentage(totalCorrectAnswersCount, questions.size()))
                .answersInfo(answersInfo)
                .build();
    }

    private static String calculatePercentage(int numerator, int denominator) {
        if (denominator == 0) {
            return "NaN";
        }
        double result = ((double) numerator / denominator) * 100;
        return String.format("%.2f", result);
    }
}
