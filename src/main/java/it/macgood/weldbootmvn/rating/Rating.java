package it.macgood.weldbootmvn.rating;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.macgood.weldbootmvn.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private User user;
    @CreationTimestamp
    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    @Column(name = "solution_date_time")
    private LocalDateTime rateDate;
    private Double detectFreePercent;
}
