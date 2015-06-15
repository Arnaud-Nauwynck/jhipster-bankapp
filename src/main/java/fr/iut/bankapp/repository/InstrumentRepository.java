package fr.iut.bankapp.repository;

import fr.iut.bankapp.domain.Instrument;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Instrument entity.
 */
@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

}
