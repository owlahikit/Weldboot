package it.macgood.weldbootmvn.photo;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.macgood.weldbootmvn.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "upload_history")
public class UploadHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    private String photoFileName;

    private String photoUrl;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    @CreationTimestamp
    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    @Column(name = "solution_date_time")
    private LocalDateTime uploadDateTime;

    private Double percentage;

    @Enumerated(EnumType.STRING)
    private CheckingStatus checkingStatus = CheckingStatus.READY_TO_CHECK;

    public UploadHistory setDenied() {
        if (this.percentage <= 60.0) {
            this.checkingStatus = CheckingStatus.DENIED_AUTO;
        }
        return this;
    }
    public UploadHistory setDeniedByDefectoscopist() {
        this.checkingStatus = CheckingStatus.DENIED_BY_DF;
        return this;
    }
    public UploadHistory setChecked() {
        this.checkingStatus = CheckingStatus.CHECKED;
        return this;
    }
    public UploadHistory setUndefined() {
        this.checkingStatus = CheckingStatus.UNDEFINED;
        return this;
    }
}
