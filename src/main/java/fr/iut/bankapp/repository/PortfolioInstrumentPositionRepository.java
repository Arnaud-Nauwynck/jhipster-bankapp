package fr.iut.bankapp.repository;

import fr.iut.bankapp.domain.PortfolioInstrumentPosition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PortfolioInstrumentPosition entity.
 */
@Repository
public interface PortfolioInstrumentPositionRepository extends JpaRepository<PortfolioInstrumentPosition, Long> {

}
