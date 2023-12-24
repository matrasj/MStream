package pl.matrasj.quiz.quizquestionanswer;

import java.util.List;
import java.util.stream.Collectors;

public class QuizQuestionAnswerMapper {
    public static QuizQuestionAnswerDto toDto(QuizQuestionAnswerEntity quizQuestionAnswerEntity) {
        return QuizQuestionAnswerDto.builder()
                .id(quizQuestionAnswerEntity.getId())
                .content(quizQuestionAnswerEntity.getContent())
                .correct(quizQuestionAnswerEntity.isCorrect())
                .build();
    }

    public static List<QuizQuestionAnswerDto> toDto(List<QuizQuestionAnswerEntity> quizQuestionAnswerEntities) {
        return quizQuestionAnswerEntities
                .stream()
                .map(QuizQuestionAnswerMapper::toDto)
                .collect(Collectors.toList());
    }
}
