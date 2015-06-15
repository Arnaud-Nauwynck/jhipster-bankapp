package fr.iut.bankapp.web.rest;

import com.codahale.metrics.annotation.Timed;

import fr.iut.bankapp.domain.PortfolioInstrumentPosition;
import fr.iut.bankapp.repository.PortfolioInstrumentPositionRepository;

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
 * REST controller for managing PortfolioInstrumentPosition.
 */
@RestController
@RequestMapping("/app")
public class PortfolioInstrumentPositionResource {

    private final Logger log = LoggerFactory.getLogger(PortfolioInstrumentPositionResource.class);

    @Inject
    private PortfolioInstrumentPositionRepository portfolioInstrumentPositionRepository;

    /**
     * POST  /rest/portfolioInstrumentPositions -> Create a new portfolioInstrumentPosition.
     */
    @RequestMapping(value = "/rest/portfolioInstrumentPositions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody PortfolioInstrumentPosition portfolioInstrumentPosition) {
        log.debug("REST request to save PortfolioInstrumentPosition : {}", portfolioInstrumentPosition);
        portfolioInstrumentPositionRepository.save(portfolioInstrumentPosition);
    }

    /**
     * GET  /rest/portfolioInstrumentPositions -> get all the portfolioInstrumentPositions.
     */
    @RequestMapping(value = "/rest/portfolioInstrumentPositions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PortfolioInstrumentPosition> getAll() {
        log.debug("REST request to get all PortfolioInstrumentPositions");
        return portfolioInstrumentPositionRepository.findAll();
    }

    /**
     * GET  /rest/portfolioInstrumentPositions/:id -> get the "id" portfolioInstrumentPosition.
     */
    @RequestMapping(value = "/rest/portfolioInstrumentPositions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PortfolioInstrumentPosition> get(@PathVariable Long id) {
        log.debug("REST request to get PortfolioInstrumentPosition : {}", id);
        return Optional.ofNullable(portfolioInstrumentPositionRepository.findOne(id))
            .map(portfolioInstrumentPosition -> new ResponseEntity<>(
                portfolioInstrumentPosition,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/portfolioInstrumentPositions/:id -> delete the "id" portfolioInstrumentPosition.
     */
    @RequestMapping(value = "/rest/portfolioInstrumentPositions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete PortfolioInstrumentPosition : {}", id);
        portfolioInstrumentPositionRepository.delete(id);
    }
}
