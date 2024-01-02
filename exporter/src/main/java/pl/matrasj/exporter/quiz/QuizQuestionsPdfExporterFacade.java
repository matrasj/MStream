package pl.matrasj.exporter.quiz;

import com.lowagie.text.pdf.PdfWriter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import com.lowagie.text.*;
import org.springframework.stereotype.Service;

@Service
public class QuizQuestionsPdfExporterFacade {

    public ResponseEntity<InputStreamResource> exportQuestionsToPdf(List<Long> quizQuestionIds) {
        try {
            Document document = new Document();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            document.add(new Paragraph("Hello, this is your PDF content!"));
            writer.add(new Paragraph("SDf"));
            document.close();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=questions.pdf");

            // Set the appropriate content type for PDF
            MediaType mediaType = MediaType.APPLICATION_PDF;

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(mediaType)
                    .body(new InputStreamResource(byteArrayInputStream));
        } catch (Exception e) {
            throw new ErrorDuringGeneratingPdfException();
        }
    }
}
