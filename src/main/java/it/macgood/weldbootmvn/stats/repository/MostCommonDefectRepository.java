package it.macgood.weldbootmvn.stats.repository;

import it.macgood.weldbootmvn.stats.model.MostCommonDefect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MostCommonDefectRepository extends JpaRepository<MostCommonDefect, Long> {
}
