package it.macgood.weldbootmvn.photo.presentation;

import it.macgood.weldbootmvn.photo.UploadHistory;

import java.time.format.DateTimeFormatter;

public record UploadHistoryResponse(
        Long id,
        String photoFileName,
        String photoUrl,
        String metadata,
        String uploadDateTime,
        Double percentage,
        String status
) {
    public static UploadHistoryResponse toResponse(UploadHistory history) {
        return new UploadHistoryResponse(
                history.getId(),
                history.getPhotoFileName(),
                history.getPhotoUrl(),
                history.getMetadata(),
                history.getUploadDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy")),
                history.getPercentage(),
                history.getCheckingStatus().name()
        );
    }
}
