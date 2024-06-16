package it.macgood.weldbootmvn.photo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.macgood.weldbootmvn.files.PhotoService;
import it.macgood.weldbootmvn.photo.presentation.DefectExplanation;
import it.macgood.weldbootmvn.photo.presentation.FileModel;
import it.macgood.weldbootmvn.photo.presentation.PhotoData;
import it.macgood.weldbootmvn.photo.presentation.UploadHistoryResponse;
import it.macgood.weldbootmvn.stats.model.DefectCategory;
import it.macgood.weldbootmvn.stats.model.ModelClass;
import it.macgood.weldbootmvn.stats.repository.DefectCategoryRepository;
import it.macgood.weldbootmvn.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("api/v1/photo")
public class PhotoController {
    private final UploadingHistoryRepository uploadingHistoryRepository;
    private final DefectCategoryRepository defectCategoryRepository;
    private final RestClient restClient;
    private final WebClient webClient = WebClient.create();

    private final PhotoService photoService;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/load",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> load(
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("data") String data
    ) throws IOException {

        PhotoData photoData = mapper.readValue(data, PhotoData.class);

        String fileUrl = photoService.getFileUrl(photo, 1L);
        UploadHistory uploadHistory = UploadHistory.builder()
                .photoFileName(photo.getOriginalFilename())
                .photoUrl(fileUrl)
                .metadata(data)
                .uploadDateTime(LocalDateTime.now())
                .checkingStatus(CheckingStatus.READY_TO_CHECK)
                .percentage(69.0)
                .build();
        uploadingHistoryRepository.save(uploadHistory);

        var models = new ArrayList<ModelClass>();

        if (photoData.data1() > 0) {
            models.add(ModelClass.ADJ);
        }
        if (photoData.data2() > 0) {
            models.add(ModelClass.INT);
        }
        if (photoData.data3() > 0) {
            models.add(ModelClass.GEO);
        }
        if (photoData.data4() > 0) {
            models.add(ModelClass.NON);
        }
        if (photoData.data5() > 0) {
            models.add(ModelClass.PRO);
        }

        DefectExplanation defectExplanation = new DefectExplanation();

        defectExplanation.setReasons(models);
        defectExplanation.setDetected(models);
        defectExplanation.setRemoval(models);
        defectExplanation.setPhotoUrl(fileUrl);

        return ResponseEntity.ok(defectExplanation);
    }

    @GetMapping("/storyByUserId/{userId}")
    public ResponseEntity<?> getStoryByUserId(@PathVariable Long userId) {
        List<UploadHistory> userHistory = uploadingHistoryRepository.findByUserId(userId);
        var history = userHistory.stream()
                .map(UploadHistoryResponse::toResponse)
                .sorted(Comparator.comparing(UploadHistoryResponse::uploadDateTime))
                .toList();
        return ResponseEntity.ok(history);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getStory() {
        List<UploadHistory> userHistory = uploadingHistoryRepository.findAll();
        var history = userHistory.stream()
               .map(UploadHistoryResponse::toResponse)
               .sorted(Comparator.comparing(UploadHistoryResponse::uploadDateTime))
               .toList();
        return ResponseEntity.ok(history);
    }




//    @PostMapping("/upload")
//    public Mono<ResponseEntity<Void>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
//        byte[] fileBytes = file.getBytes();
//
//        MultipartClientRequestPart multipartFile =
//                new MultipartClientRequestPart("file", file.getOriginalFilename(),
//                        MediaType.parseMediaType(file.getContentType()), fileBytes);
//
//        return webClient.post()
//                .uri("http://localhost:8080/upload") // Замените на URL вашего сервера
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .body(body -> body.)
//                .exchange()
//                .flatMap(response -> Mono.fromCallable(() -> ResponseEntity.ok().build()));
//    }
}
