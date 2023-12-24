package pl.matrasj.quiz.quizquestion;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-question")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizQuestionController {
    QuizQuestionRepository quizQuestionRepository;

    @PostMapping
    public ResponseEntity<List<QuizQuestionDto>> getQuiz(@RequestBody @Valid QuizGenerationPayloadRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(QuizQuestionMapper.toDto(
                        quizQuestionRepository.getRandomActiveQuestionsByCategories(
                                request.getQuizCategoryIds(),
                                request.getCount())
                ));
    }
}
