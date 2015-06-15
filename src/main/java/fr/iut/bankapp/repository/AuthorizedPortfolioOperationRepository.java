package fr.iut.bankapp.repository;

import fr.iut.bankapp.domain.AuthorizedPortfolioOperation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AuthorizedPortfolioOperation entity.
 */
@Repository
public interface AuthorizedPortfolioOperationRepository extends JpaRepository<AuthorizedPortfolioOperation, Long> {

}
