package fr.iut.bankapp.repository;

import fr.iut.bankapp.domain.FinOperation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FinOperation entity.
 */
@Repository
public interface FinOperationRepository extends JpaRepository<FinOperation, Long> {

}
