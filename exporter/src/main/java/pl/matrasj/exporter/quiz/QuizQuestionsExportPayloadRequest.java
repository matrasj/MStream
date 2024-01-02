package pl.matrasj.exporter.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuizQuestionsExportPayloadRequest {
    private List<Long> quizQuestionIds;
}
