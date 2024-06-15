package it.macgood.weldbootmvn.stats;

import it.macgood.weldbootmvn.stats.repository.DefectCategoryRepository;
import it.macgood.weldbootmvn.stats.repository.DefectQuantityRepository;
import it.macgood.weldbootmvn.stats.repository.MostCommonDefectRepository;
import it.macgood.weldbootmvn.stats.repository.PercentOfDefectsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/text-stats")
public class StatisticsController {
    private final DefectCategoryRepository defectCategoryRepository;
    private final PercentOfDefectsRepository percentOfDefectsRepository;
    private final MostCommonDefectRepository mostCommonDefectRepository;
    private final DefectQuantityRepository defectQuantityRepository;

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(defectCategoryRepository.findAll());
    }

    @GetMapping("/percent-of-defects")
    public ResponseEntity<?> getPercentOfDefects() {
        return ResponseEntity.ok(percentOfDefectsRepository.findAll());
    }

    @GetMapping("/most-common")
    public ResponseEntity<?> getMostCommonDefects() {
        return ResponseEntity.ok(defectQuantityRepository.findAllByOrderByQuantityDesc());
    }


}
