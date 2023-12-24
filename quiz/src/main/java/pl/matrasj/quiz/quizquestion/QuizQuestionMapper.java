package pl.matrasj.quiz.quizquestion;

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
}
