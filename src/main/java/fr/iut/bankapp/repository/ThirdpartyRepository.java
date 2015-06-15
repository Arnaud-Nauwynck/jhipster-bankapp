package fr.iut.bankapp.repository;

import fr.iut.bankapp.domain.Thirdparty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Thirdparty entity.
 */
@Repository
public interface ThirdpartyRepository extends JpaRepository<Thirdparty, Long> {

}
