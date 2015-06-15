package fr.iut.bankapp.web.rest;

import com.codahale.metrics.annotation.Timed;

import fr.iut.bankapp.domain.Thirdparty;
import fr.iut.bankapp.repository.ThirdpartyRepository;

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
 * REST controller for managing Thirdparty.
 */
@RestController
@RequestMapping("/app")
public class ThirdpartyResource {

    private final Logger log = LoggerFactory.getLogger(ThirdpartyResource.class);

    @Inject
    private ThirdpartyRepository thirdpartyRepository;

    /**
     * POST  /rest/thirdpartys -> Create a new thirdparty.
     */
    @RequestMapping(value = "/rest/thirdpartys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Thirdparty thirdparty) {
        log.debug("REST request to save Thirdparty : {}", thirdparty);
        thirdpartyRepository.save(thirdparty);
    }

    /**
     * GET  /rest/thirdpartys -> get all the thirdpartys.
     */
    @RequestMapping(value = "/rest/thirdpartys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Thirdparty> getAll() {
        log.debug("REST request to get all Thirdpartys");
        return thirdpartyRepository.findAll();
    }

    /**
     * GET  /rest/thirdpartys/:id -> get the "id" thirdparty.
     */
    @RequestMapping(value = "/rest/thirdpartys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Thirdparty> get(@PathVariable Long id) {
        log.debug("REST request to get Thirdparty : {}", id);
        return Optional.ofNullable(thirdpartyRepository.findOne(id))
            .map(thirdparty -> new ResponseEntity<>(
                thirdparty,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/thirdpartys/:id -> delete the "id" thirdparty.
     */
    @RequestMapping(value = "/rest/thirdpartys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Thirdparty : {}", id);
        thirdpartyRepository.delete(id);
    }
}
