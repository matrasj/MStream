package pl.matrasj.quiz.quizcategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizCategoryDto {
    private Long id;
    private String name;
    private boolean active;
}
