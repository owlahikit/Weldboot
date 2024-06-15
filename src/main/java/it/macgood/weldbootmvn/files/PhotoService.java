package it.macgood.weldbootmvn.files;

import it.macgood.weldbootmvn.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final ResourceLoader resourceLoader;
    private final UserRepository userRepository;

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;
    public String getFileUrl(
            MultipartFile file,
            Long id
    ) throws IOException {
        Path dirPath = Paths.get("src/main/resources/static/", "" + id);
        if (!Files.exists(dirPath)) {
            try {
                Files.createDirectories(dirPath);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Could not create directory: " + dirPath, e);
            }
        }

        Path filePath = dirPath.resolve(file.getOriginalFilename());

        String encode = Base64Util.encode("" + filePath);

        String apiFilePath = "http://localhost:8081/api/v1/files/" + encode;

        try {
            Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not write file: " + filePath, e);
        }
        userRepository.findById(id).ifPresent(user -> {
            user.setAvatar(apiFilePath);
            userRepository.save(user);
        });
        return apiFilePath;
    }
}
