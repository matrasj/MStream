package pl.matrasj.quiz.quizcategory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizCategoryController {
    QuizCategoryRepository quizCategoryRepository;

    @GetMapping
    public ResponseEntity<List<QuizCategoryDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(QuizCategoryMapper.toDto(quizCategoryRepository.findAll()));
    }
}
