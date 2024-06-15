package it.macgood.weldbootmvn.stats.model;

import jakarta.persistence.*;

import java.util.List;

@Table
@Entity

public class MostCommonDefect {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToMany
    private List<DefectCategory> defects;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="most_common_defect_id")
    private DefectCategory mostCommonDefect;
}
