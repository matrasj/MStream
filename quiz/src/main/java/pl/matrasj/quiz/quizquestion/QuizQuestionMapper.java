package pl.matrasj.quiz.quizquestion;

import pl.matrasj.quiz.quizquestion.dto.QuizQuestionDto;
import pl.matrasj.quiz.quizquestion.dto.QuizQuestionWithInfoAboutCorrectAnswersDto;
import pl.matrasj.quiz.quizquestionanswer.QuizQuestionAnswerMapper;

import java.util.List;
import java.util.stream.Collectors;

public class QuizQuestionMapper {
    public static QuizQuestionDto toDto(QuizQuestionEntity quizQuestionEntity) {
        return QuizQuestionDto.builder()
                .id(quizQuestionEntity.getId())
                .content(quizQuestionEntity.getContent())
                .answers(QuizQuestionAnswerMapper.toDto(quizQuestionEntity.getAnswers()))
                .build();
    }

    public static List<QuizQuestionDto> toDto(List<QuizQuestionEntity> quizQuestions) {
        return quizQuestions
                .stream()
                .map(QuizQuestionMapper::toDto)
                .collect(Collectors.toList());
    }

    public static QuizQuestionWithInfoAboutCorrectAnswersDto toDtoWithCorrectInfo(QuizQuestionEntity quizQuestionEntity) {
        return QuizQuestionWithInfoAboutCorrectAnswersDto.builder()
                .id(quizQuestionEntity.getId())
                .content(quizQuestionEntity.getContent())
                .answers(QuizQuestionAnswerMapper.toDtoWithCorrectInfo(quizQuestionEntity.getAnswers()))
                .build();
    }

    public static List<QuizQuestionWithInfoAboutCorrectAnswersDto> toDtoWithCorrectInfo(List<QuizQuestionEntity> quizQuestions) {
        return quizQuestions
                .stream()
                .map(QuizQuestionMapper::toDtoWithCorrectInfo)
                .collect(Collectors.toList());
    }
}
