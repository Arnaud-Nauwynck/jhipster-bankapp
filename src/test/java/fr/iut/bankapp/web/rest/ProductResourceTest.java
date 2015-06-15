package fr.iut.bankapp.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.math.BigDecimal;
import java.util.List;

import fr.iut.bankapp.Application;
import fr.iut.bankapp.domain.Product;
import fr.iut.bankapp.repository.ProductRepository;
import fr.iut.bankapp.web.rest.ProductResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ProductResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final String DEFAULT_SHORT_DESCR = "SAMPLE_TEXT";
    private static final String UPDATED_SHORT_DESCR = "UPDATED_TEXT";
    
    private static final String DEFAULT_LONG_DESCR = "SAMPLE_TEXT";
    private static final String UPDATED_LONG_DESCR = "UPDATED_TEXT";
    
    private static final String DEFAULT_PHOTO_URL = "SAMPLE_TEXT";
    private static final String UPDATED_PHOTO_URL = "UPDATED_TEXT";
    
    private static final BigDecimal DEFAULT_PRICE = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_PRICE = BigDecimal.ONE;
    

    @Inject
    private ProductRepository productRepository;

    private MockMvc restProductMockMvc;

    private Product product;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductResource productResource = new ProductResource();
        ReflectionTestUtils.setField(productResource, "productRepository", productRepository);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource).build();
    }

    @Before
    public void initTest() {
        product = new Product();
        product.setName(DEFAULT_NAME);
        product.setShortDescr(DEFAULT_SHORT_DESCR);
        product.setLongDescr(DEFAULT_LONG_DESCR);
        product.setPhotoURL(DEFAULT_PHOTO_URL);
        product.setPrice(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        // Validate the database is empty
        assertThat(productRepository.findAll()).hasSize(0);

        // Create the Product
        restProductMockMvc.perform(post("/app/rest/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(product)))
                .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1);
        Product testProduct = products.iterator().next();
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getShortDescr()).isEqualTo(DEFAULT_SHORT_DESCR);
        assertThat(testProduct.getLongDescr()).isEqualTo(DEFAULT_LONG_DESCR);
        assertThat(testProduct.getPhotoURL()).isEqualTo(DEFAULT_PHOTO_URL);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the products
        restProductMockMvc.perform(get("/app/rest/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(product.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].shortDescr").value(DEFAULT_SHORT_DESCR.toString()))
                .andExpect(jsonPath("$.[0].longDescr").value(DEFAULT_LONG_DESCR.toString()))
                .andExpect(jsonPath("$.[0].photoURL").value(DEFAULT_PHOTO_URL.toString()))
                .andExpect(jsonPath("$.[0].price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/app/rest/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shortDescr").value(DEFAULT_SHORT_DESCR.toString()))
            .andExpect(jsonPath("$.longDescr").value(DEFAULT_LONG_DESCR.toString()))
            .andExpect(jsonPath("$.photoURL").value(DEFAULT_PHOTO_URL.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/app/rest/products/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Update the product
        product.setName(UPDATED_NAME);
        product.setShortDescr(UPDATED_SHORT_DESCR);
        product.setLongDescr(UPDATED_LONG_DESCR);
        product.setPhotoURL(UPDATED_PHOTO_URL);
        product.setPrice(UPDATED_PRICE);
        restProductMockMvc.perform(post("/app/rest/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(product)))
                .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1);
        Product testProduct = products.iterator().next();
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getShortDescr()).isEqualTo(UPDATED_SHORT_DESCR);
        assertThat(testProduct.getLongDescr()).isEqualTo(UPDATED_LONG_DESCR);
        assertThat(testProduct.getPhotoURL()).isEqualTo(UPDATED_PHOTO_URL);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(delete("/app/rest/products/{id}", product.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(0);
    }
}
