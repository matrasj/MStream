package pl.matrasj.user.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileSaver {
    public String saveFile(byte[] fileBytes, String pathDir, String fileName) throws IOException {
        Path uploadPath = Paths.get(pathDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, fileBytes);
        log.info(filePath.toString());
        return String.format("%s//%s", pathDir, fileName);
    }
}
