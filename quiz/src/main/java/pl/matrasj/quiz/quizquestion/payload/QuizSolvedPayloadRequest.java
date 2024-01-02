package pl.matrasj.quiz.quizquestion.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuizSolvedPayloadRequest {
    @NotNull
    private Map<Long, List<Long>> solutions;
}
