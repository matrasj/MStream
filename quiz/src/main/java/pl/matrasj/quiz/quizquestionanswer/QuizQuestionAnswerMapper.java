package pl.matrasj.quiz.quizquestionanswer;

import pl.matrasj.quiz.quizquestionanswer.dto.QuizQuestionAnswerDto;
import pl.matrasj.quiz.quizquestionanswer.dto.QuizQuestionAnswerWithCorrectInfoDto;

import java.util.List;
import java.util.stream.Collectors;

public class QuizQuestionAnswerMapper {
    public static QuizQuestionAnswerDto toDto(QuizQuestionAnswerEntity quizQuestionAnswerEntity) {
        return QuizQuestionAnswerDto.builder()
                .id(quizQuestionAnswerEntity.getId())
                .content(quizQuestionAnswerEntity.getContent())
                .build();
    }

    public static List<QuizQuestionAnswerDto> toDto(List<QuizQuestionAnswerEntity> quizQuestionAnswerEntities) {
        return quizQuestionAnswerEntities
                .stream()
                .map(QuizQuestionAnswerMapper::toDto)
                .collect(Collectors.toList());
    }

    public static QuizQuestionAnswerWithCorrectInfoDto toDtoWithCorrectInfo(QuizQuestionAnswerEntity quizQuestionAnswerEntity) {
        return QuizQuestionAnswerWithCorrectInfoDto.builder()
                .id(quizQuestionAnswerEntity.getId())
                .correct(quizQuestionAnswerEntity.isCorrect())
                .content(quizQuestionAnswerEntity.getContent())
                .build();
    }

    public static List<QuizQuestionAnswerWithCorrectInfoDto> toDtoWithCorrectInfo(List<QuizQuestionAnswerEntity> quizQuestionAnswerEntities) {
        return quizQuestionAnswerEntities
                .stream()
                .map(QuizQuestionAnswerMapper::toDtoWithCorrectInfo)
                .collect(Collectors.toList());
    }
}
