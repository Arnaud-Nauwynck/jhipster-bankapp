package fr.iut.bankapp.web.rest;

import com.codahale.metrics.annotation.Timed;

import fr.iut.bankapp.domain.FinOperation;
import fr.iut.bankapp.repository.FinOperationRepository;

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
 * REST controller for managing FinOperation.
 */
@RestController
@RequestMapping("/app")
public class FinOperationResource {

    private final Logger log = LoggerFactory.getLogger(FinOperationResource.class);

    @Inject
    private FinOperationRepository finOperationRepository;

    /**
     * POST  /rest/finOperations -> Create a new finOperation.
     */
    @RequestMapping(value = "/rest/finOperations",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody FinOperation finOperation) {
        log.debug("REST request to save FinOperation : {}", finOperation);
        finOperationRepository.save(finOperation);
    }

    /**
     * GET  /rest/finOperations -> get all the finOperations.
     */
    @RequestMapping(value = "/rest/finOperations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<FinOperation> getAll() {
        log.debug("REST request to get all FinOperations");
        return finOperationRepository.findAll();
    }

    /**
     * GET  /rest/finOperations/:id -> get the "id" finOperation.
     */
    @RequestMapping(value = "/rest/finOperations/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FinOperation> get(@PathVariable Long id) {
        log.debug("REST request to get FinOperation : {}", id);
        return Optional.ofNullable(finOperationRepository.findOne(id))
            .map(finOperation -> new ResponseEntity<>(
                finOperation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/finOperations/:id -> delete the "id" finOperation.
     */
    @RequestMapping(value = "/rest/finOperations/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete FinOperation : {}", id);
        finOperationRepository.delete(id);
    }
}
