package pl.matrasj.user.account;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileSaver {

    private static final String AVATAR_UPLOAD_DIR = "C://avatars";

    public void saveAvatar(byte[] fileBytes, String fileName) throws IOException {
        Path uploadPath = Paths.get(AVATAR_UPLOAD_DIR);

        // Create the avatars directory if it doesn't exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save the file to the avatars directory
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, fileBytes);
    }
}
