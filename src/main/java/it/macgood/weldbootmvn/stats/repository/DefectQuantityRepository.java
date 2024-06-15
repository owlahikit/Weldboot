package it.macgood.weldbootmvn.stats.repository;

import it.macgood.weldbootmvn.stats.model.DefectQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefectQuantityRepository extends JpaRepository<DefectQuantity, Long> {
    List<DefectQuantity> findAllByOrderByQuantityDesc();
    List<DefectQuantity> findTopNByQuantity(Integer n);
}
