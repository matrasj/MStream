package pl.matrasj.quiz.quizcategory;

import java.util.List;
import java.util.stream.Collectors;

public class QuizCategoryMapper {
    public static QuizCategoryDto toDto(QuizCategoryEntity quizCategoryEntity) {
        return QuizCategoryDto.builder()
                .id(quizCategoryEntity.getId())
                .name(quizCategoryEntity.getName())
                .active(quizCategoryEntity.isActive())
                .build();
    }

    public static List<QuizCategoryDto> toDto(List<QuizCategoryEntity> quizCategoryEntities) {
        return quizCategoryEntities
                .stream()
                .map(QuizCategoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
