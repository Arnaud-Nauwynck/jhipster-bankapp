package fr.iut.bankapp.web.rest;

import com.codahale.metrics.annotation.Timed;

import fr.iut.bankapp.domain.Instrument;
import fr.iut.bankapp.repository.InstrumentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Instrument.
 */
@RestController
@RequestMapping("/app")
public class InstrumentResource {

    private final Logger log = LoggerFactory.getLogger(InstrumentResource.class);

    @Inject
    private InstrumentRepository instrumentRepository;

    /**
     * POST  /rest/instruments -> Create a new instrument.
     */
    @RequestMapping(value = "/rest/instruments",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Instrument instrument) {
        log.debug("REST request to save Instrument : {}", instrument);
        instrumentRepository.save(instrument);
    }

    /**
     * GET  /rest/instruments -> get all the instruments.
     */
    @RequestMapping(value = "/rest/instruments",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Instrument> getAll() {
        log.debug("REST request to get all Instruments");
        return instrumentRepository.findAll();
    }

    /**
     * GET  /rest/instruments/:id -> get the "id" instrument.
     */
    @RequestMapping(value = "/rest/instruments/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Instrument> get(@PathVariable Long id) {
        log.debug("REST request to get Instrument : {}", id);
        return Optional.ofNullable(instrumentRepository.findOne(id))
            .map(instrument -> new ResponseEntity<>(
                instrument,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/instruments/:id -> delete the "id" instrument.
     */
    @RequestMapping(value = "/rest/instruments/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Instrument : {}", id);
        instrumentRepository.delete(id);
    }
}
