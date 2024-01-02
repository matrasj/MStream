package pl.matrasj.quiz.quizquestion.payload;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuizGenerationPayloadRequest {
    @Min(1)
    @Max(100)
    private int count;
    @NotNull
    private List<Long> quizCategoryIds;
}
