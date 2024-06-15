package it.macgood.weldbootmvn.stats.repository;

import it.macgood.weldbootmvn.stats.model.DefectCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefectCategoryRepository extends JpaRepository<DefectCategory, Long> {

}