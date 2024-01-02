package pl.matrasj.exporter.quiz;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizQuestionsPdfExportController {
    QuizQuestionsPdfExporterFacade quizQuestionsPdfExporterFacade;
    @PostMapping("/questions")
    public ResponseEntity<InputStreamResource> exportQuestionsToPdf(@RequestBody List<Long> quizQuestionIds) {
        return quizQuestionsPdfExporterFacade.exportQuestionsToPdf(quizQuestionIds);
    }
}
