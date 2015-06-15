package fr.iut.bankapp.web.rest;

import com.codahale.metrics.annotation.Timed;

import fr.iut.bankapp.domain.Product;
import fr.iut.bankapp.repository.ProductRepository;

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
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/app")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    @Inject
    private ProductRepository productRepository;

    /**
     * POST  /rest/products -> Create a new product.
     */
    @RequestMapping(value = "/rest/products",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Product product) {
        log.debug("REST request to save Product : {}", product);
        productRepository.save(product);
    }

    /**
     * GET  /rest/products -> get all the products.
     */
    @RequestMapping(value = "/rest/products",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Product> getAll() {
        log.debug("REST request to get all Products");
        return productRepository.findAll();
    }

    /**
     * GET  /rest/products/:id -> get the "id" product.
     */
    @RequestMapping(value = "/rest/products/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Product> get(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        return Optional.ofNullable(productRepository.findOne(id))
            .map(product -> new ResponseEntity<>(
                product,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/products/:id -> delete the "id" product.
     */
    @RequestMapping(value = "/rest/products/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productRepository.delete(id);
    }
}
