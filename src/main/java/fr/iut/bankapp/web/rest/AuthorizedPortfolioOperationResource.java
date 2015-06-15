package fr.iut.bankapp.web.rest;

import com.codahale.metrics.annotation.Timed;

import fr.iut.bankapp.domain.AuthorizedPortfolioOperation;
import fr.iut.bankapp.repository.AuthorizedPortfolioOperationRepository;

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
 * REST controller for managing AuthorizedPortfolioOperation.
 */
@RestController
@RequestMapping("/app")
public class AuthorizedPortfolioOperationResource {

    private final Logger log = LoggerFactory.getLogger(AuthorizedPortfolioOperationResource.class);

    @Inject
    private AuthorizedPortfolioOperationRepository authorizedPortfolioOperationRepository;

    /**
     * POST  /rest/authorizedPortfolioOperations -> Create a new authorizedPortfolioOperation.
     */
    @RequestMapping(value = "/rest/authorizedPortfolioOperations",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody AuthorizedPortfolioOperation authorizedPortfolioOperation) {
        log.debug("REST request to save AuthorizedPortfolioOperation : {}", authorizedPortfolioOperation);
        authorizedPortfolioOperationRepository.save(authorizedPortfolioOperation);
    }

    /**
     * GET  /rest/authorizedPortfolioOperations -> get all the authorizedPortfolioOperations.
     */
    @RequestMapping(value = "/rest/authorizedPortfolioOperations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AuthorizedPortfolioOperation> getAll() {
        log.debug("REST request to get all AuthorizedPortfolioOperations");
        return authorizedPortfolioOperationRepository.findAll();
    }

    /**
     * GET  /rest/authorizedPortfolioOperations/:id -> get the "id" authorizedPortfolioOperation.
     */
    @RequestMapping(value = "/rest/authorizedPortfolioOperations/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthorizedPortfolioOperation> get(@PathVariable Long id) {
        log.debug("REST request to get AuthorizedPortfolioOperation : {}", id);
        return Optional.ofNullable(authorizedPortfolioOperationRepository.findOne(id))
            .map(authorizedPortfolioOperation -> new ResponseEntity<>(
                authorizedPortfolioOperation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/authorizedPortfolioOperations/:id -> delete the "id" authorizedPortfolioOperation.
     */
    @RequestMapping(value = "/rest/authorizedPortfolioOperations/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete AuthorizedPortfolioOperation : {}", id);
        authorizedPortfolioOperationRepository.delete(id);
    }
}
