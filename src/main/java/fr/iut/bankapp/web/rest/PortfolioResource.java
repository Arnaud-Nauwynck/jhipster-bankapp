package fr.iut.bankapp.web.rest;

import com.codahale.metrics.annotation.Timed;

import fr.iut.bankapp.domain.Portfolio;
import fr.iut.bankapp.repository.PortfolioRepository;

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
 * REST controller for managing Portfolio.
 */
@RestController
@RequestMapping("/app")
public class PortfolioResource {

    private final Logger log = LoggerFactory.getLogger(PortfolioResource.class);

    @Inject
    private PortfolioRepository portfolioRepository;

    /**
     * POST  /rest/portfolios -> Create a new portfolio.
     */
    @RequestMapping(value = "/rest/portfolios",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Portfolio portfolio) {
        log.debug("REST request to save Portfolio : {}", portfolio);
        portfolioRepository.save(portfolio);
    }

    /**
     * GET  /rest/portfolios -> get all the portfolios.
     */
    @RequestMapping(value = "/rest/portfolios",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Portfolio> getAll() {
        log.debug("REST request to get all Portfolios");
        return portfolioRepository.findAll();
    }

    /**
     * GET  /rest/portfolios/:id -> get the "id" portfolio.
     */
    @RequestMapping(value = "/rest/portfolios/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Portfolio> get(@PathVariable Long id) {
        log.debug("REST request to get Portfolio : {}", id);
        return Optional.ofNullable(portfolioRepository.findOne(id))
            .map(portfolio -> new ResponseEntity<>(
                portfolio,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/portfolios/:id -> delete the "id" portfolio.
     */
    @RequestMapping(value = "/rest/portfolios/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Portfolio : {}", id);
        portfolioRepository.delete(id);
    }
}
