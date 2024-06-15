package it.macgood.weldbootmvn.photo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UploadingHistoryRepository extends JpaRepository<UploadHistory, Long> {
    List<UploadHistory> findByUserId(Long userId);
//    @Query("SELECT * FROM upload_history WHERE checking_status is not")
//    List<UploadHistory> findAllByCheckingStatus()
}
