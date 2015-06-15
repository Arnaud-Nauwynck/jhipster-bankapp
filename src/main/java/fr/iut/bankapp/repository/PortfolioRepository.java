package fr.iut.bankapp.repository;

import fr.iut.bankapp.domain.Portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Portfolio entity.
 */
@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

}
