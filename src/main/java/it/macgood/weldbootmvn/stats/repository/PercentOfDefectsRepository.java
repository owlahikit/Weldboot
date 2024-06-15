package it.macgood.weldbootmvn.stats.repository;

import it.macgood.weldbootmvn.stats.model.PercentOfDefects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PercentOfDefectsRepository extends JpaRepository<PercentOfDefects, Long> {
}
