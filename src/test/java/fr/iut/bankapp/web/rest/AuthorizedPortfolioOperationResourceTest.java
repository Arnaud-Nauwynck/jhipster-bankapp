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
import fr.iut.bankapp.domain.AuthorizedPortfolioOperation;
import fr.iut.bankapp.repository.AuthorizedPortfolioOperationRepository;
import fr.iut.bankapp.web.rest.AuthorizedPortfolioOperationResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AuthorizedPortfolioOperationResource REST controller.
 *
 * @see AuthorizedPortfolioOperationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class AuthorizedPortfolioOperationResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final String DEFAULT_OPER_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_OPER_TYPE = "UPDATED_TEXT";
    
    private static final BigDecimal DEFAULT_MIN_QUANTITY = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_MIN_QUANTITY = BigDecimal.ONE;
    
    private static final BigDecimal DEFAULT_MAX_QUANTITY = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_MAX_QUANTITY = BigDecimal.ONE;
    

    @Inject
    private AuthorizedPortfolioOperationRepository authorizedPortfolioOperationRepository;

    private MockMvc restAuthorizedPortfolioOperationMockMvc;

    private AuthorizedPortfolioOperation authorizedPortfolioOperation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AuthorizedPortfolioOperationResource authorizedPortfolioOperationResource = new AuthorizedPortfolioOperationResource();
        ReflectionTestUtils.setField(authorizedPortfolioOperationResource, "authorizedPortfolioOperationRepository", authorizedPortfolioOperationRepository);
        this.restAuthorizedPortfolioOperationMockMvc = MockMvcBuilders.standaloneSetup(authorizedPortfolioOperationResource).build();
    }

    @Before
    public void initTest() {
        authorizedPortfolioOperation = new AuthorizedPortfolioOperation();
        authorizedPortfolioOperation.setName(DEFAULT_NAME);
        authorizedPortfolioOperation.setOperType(DEFAULT_OPER_TYPE);
        authorizedPortfolioOperation.setMinQuantity(DEFAULT_MIN_QUANTITY);
        authorizedPortfolioOperation.setMaxQuantity(DEFAULT_MAX_QUANTITY);
    }

    @Test
    @Transactional
    public void createAuthorizedPortfolioOperation() throws Exception {
        // Validate the database is empty
        assertThat(authorizedPortfolioOperationRepository.findAll()).hasSize(0);

        // Create the AuthorizedPortfolioOperation
        restAuthorizedPortfolioOperationMockMvc.perform(post("/app/rest/authorizedPortfolioOperations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(authorizedPortfolioOperation)))
                .andExpect(status().isOk());

        // Validate the AuthorizedPortfolioOperation in the database
        List<AuthorizedPortfolioOperation> authorizedPortfolioOperations = authorizedPortfolioOperationRepository.findAll();
        assertThat(authorizedPortfolioOperations).hasSize(1);
        AuthorizedPortfolioOperation testAuthorizedPortfolioOperation = authorizedPortfolioOperations.iterator().next();
        assertThat(testAuthorizedPortfolioOperation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuthorizedPortfolioOperation.getOperType()).isEqualTo(DEFAULT_OPER_TYPE);
        assertThat(testAuthorizedPortfolioOperation.getMinQuantity()).isEqualTo(DEFAULT_MIN_QUANTITY);
        assertThat(testAuthorizedPortfolioOperation.getMaxQuantity()).isEqualTo(DEFAULT_MAX_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllAuthorizedPortfolioOperations() throws Exception {
        // Initialize the database
        authorizedPortfolioOperationRepository.saveAndFlush(authorizedPortfolioOperation);

        // Get all the authorizedPortfolioOperations
        restAuthorizedPortfolioOperationMockMvc.perform(get("/app/rest/authorizedPortfolioOperations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(authorizedPortfolioOperation.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].operType").value(DEFAULT_OPER_TYPE.toString()))
                .andExpect(jsonPath("$.[0].minQuantity").value(DEFAULT_MIN_QUANTITY.intValue()))
                .andExpect(jsonPath("$.[0].maxQuantity").value(DEFAULT_MAX_QUANTITY.intValue()));
    }

    @Test
    @Transactional
    public void getAuthorizedPortfolioOperation() throws Exception {
        // Initialize the database
        authorizedPortfolioOperationRepository.saveAndFlush(authorizedPortfolioOperation);

        // Get the authorizedPortfolioOperation
        restAuthorizedPortfolioOperationMockMvc.perform(get("/app/rest/authorizedPortfolioOperations/{id}", authorizedPortfolioOperation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(authorizedPortfolioOperation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.operType").value(DEFAULT_OPER_TYPE.toString()))
            .andExpect(jsonPath("$.minQuantity").value(DEFAULT_MIN_QUANTITY.intValue()))
            .andExpect(jsonPath("$.maxQuantity").value(DEFAULT_MAX_QUANTITY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAuthorizedPortfolioOperation() throws Exception {
        // Get the authorizedPortfolioOperation
        restAuthorizedPortfolioOperationMockMvc.perform(get("/app/rest/authorizedPortfolioOperations/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuthorizedPortfolioOperation() throws Exception {
        // Initialize the database
        authorizedPortfolioOperationRepository.saveAndFlush(authorizedPortfolioOperation);

        // Update the authorizedPortfolioOperation
        authorizedPortfolioOperation.setName(UPDATED_NAME);
        authorizedPortfolioOperation.setOperType(UPDATED_OPER_TYPE);
        authorizedPortfolioOperation.setMinQuantity(UPDATED_MIN_QUANTITY);
        authorizedPortfolioOperation.setMaxQuantity(UPDATED_MAX_QUANTITY);
        restAuthorizedPortfolioOperationMockMvc.perform(post("/app/rest/authorizedPortfolioOperations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(authorizedPortfolioOperation)))
                .andExpect(status().isOk());

        // Validate the AuthorizedPortfolioOperation in the database
        List<AuthorizedPortfolioOperation> authorizedPortfolioOperations = authorizedPortfolioOperationRepository.findAll();
        assertThat(authorizedPortfolioOperations).hasSize(1);
        AuthorizedPortfolioOperation testAuthorizedPortfolioOperation = authorizedPortfolioOperations.iterator().next();
        assertThat(testAuthorizedPortfolioOperation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuthorizedPortfolioOperation.getOperType()).isEqualTo(UPDATED_OPER_TYPE);
        assertThat(testAuthorizedPortfolioOperation.getMinQuantity()).isEqualTo(UPDATED_MIN_QUANTITY);
        assertThat(testAuthorizedPortfolioOperation.getMaxQuantity()).isEqualTo(UPDATED_MAX_QUANTITY);
    }

    @Test
    @Transactional
    public void deleteAuthorizedPortfolioOperation() throws Exception {
        // Initialize the database
        authorizedPortfolioOperationRepository.saveAndFlush(authorizedPortfolioOperation);

        // Get the authorizedPortfolioOperation
        restAuthorizedPortfolioOperationMockMvc.perform(delete("/app/rest/authorizedPortfolioOperations/{id}", authorizedPortfolioOperation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AuthorizedPortfolioOperation> authorizedPortfolioOperations = authorizedPortfolioOperationRepository.findAll();
        assertThat(authorizedPortfolioOperations).hasSize(0);
    }
}
