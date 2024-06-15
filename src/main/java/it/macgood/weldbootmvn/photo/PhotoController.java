package it.macgood.weldbootmvn.photo;

import it.macgood.weldbootmvn.files.PhotoService;
import it.macgood.weldbootmvn.photo.presentation.DefectExplanation;
import it.macgood.weldbootmvn.photo.presentation.UploadHistoryResponse;
import it.macgood.weldbootmvn.stats.model.DefectCategory;
import it.macgood.weldbootmvn.stats.model.ModelClass;
import it.macgood.weldbootmvn.stats.repository.DefectCategoryRepository;
import it.macgood.weldbootmvn.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("api/v1/photo")
public class PhotoController {
    private final UploadingHistoryRepository uploadingHistoryRepository;
    private final DefectCategoryRepository defectCategoryRepository;
    private final PhotoService photoService;

    @PostMapping(
            value = "/load",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> load(
            @RequestPart("photo") MultipartFile photo
    ) throws IOException {
        // restClient.post()
        String fileUrl = photoService.getFileUrl(photo, 1L);
        UploadHistory uploadHistory = UploadHistory.builder()
                .photoFileName(photo.getOriginalFilename())
                .photoUrl(fileUrl)
                .uploadDateTime(LocalDateTime.now())
                .checkingStatus(CheckingStatus.READY_TO_CHECK)
                .percentage(69.0)
                .build();
        uploadingHistoryRepository.save(uploadHistory);

        DefectExplanation defectExplanation = new DefectExplanation();
        defectExplanation.setReasons(List.of(ModelClass.ADJ, ModelClass.GEO));
        defectExplanation.setDetected(List.of(ModelClass.ADJ, ModelClass.GEO));
        defectExplanation.setRemoval(List.of(ModelClass.ADJ, ModelClass.GEO));
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
}
