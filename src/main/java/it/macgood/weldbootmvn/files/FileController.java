package it.macgood.weldbootmvn.files;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("api/v1/files")
public class FileController {
    private final PhotoService photoService;


    @GetMapping("/{filepath}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(
            @PathVariable String filepath
    ) {
        try {
            Path file = Paths.get(Base64Util.decode(filepath));
            Resource resource = new FileSystemResource(file);

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    class Base64Util {

        public static String encode(String originalString) {
            byte[] encodedBytes = Base64.getEncoder().encode(originalString.getBytes(StandardCharsets.UTF_8));
            return new String(encodedBytes, StandardCharsets.UTF_8);
        }

        public static String decode(String encodedString) {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
            return new String(decodedBytes, StandardCharsets.UTF_8);
        }

    }
}
