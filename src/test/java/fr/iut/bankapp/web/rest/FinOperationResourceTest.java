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

import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.List;

import fr.iut.bankapp.Application;
import fr.iut.bankapp.domain.FinOperation;
import fr.iut.bankapp.repository.FinOperationRepository;
import fr.iut.bankapp.web.rest.FinOperationResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FinOperationResource REST controller.
 *
 * @see FinOperationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FinOperationResourceTest {

    private static final LocalDate DEFAULT_CREATION_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_CREATION_DATE = new LocalDate();
    
    private static final LocalDate DEFAULT_VALUE_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_VALUE_DATE = new LocalDate();
    
    private static final BigDecimal DEFAULT_QUANTITY = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_QUANTITY = BigDecimal.ONE;
    
    private static final String DEFAULT_COMMENT = "SAMPLE_TEXT";
    private static final String UPDATED_COMMENT = "UPDATED_TEXT";
    

    @Inject
    private FinOperationRepository finOperationRepository;

    private MockMvc restFinOperationMockMvc;

    private FinOperation finOperation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FinOperationResource finOperationResource = new FinOperationResource();
        ReflectionTestUtils.setField(finOperationResource, "finOperationRepository", finOperationRepository);
        this.restFinOperationMockMvc = MockMvcBuilders.standaloneSetup(finOperationResource).build();
    }

    @Before
    public void initTest() {
        finOperation = new FinOperation();
        finOperation.setCreationDate(DEFAULT_CREATION_DATE);
        finOperation.setValueDate(DEFAULT_VALUE_DATE);
        finOperation.setQuantity(DEFAULT_QUANTITY);
        finOperation.setComment(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createFinOperation() throws Exception {
        // Validate the database is empty
        assertThat(finOperationRepository.findAll()).hasSize(0);

        // Create the FinOperation
        restFinOperationMockMvc.perform(post("/app/rest/finOperations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(finOperation)))
                .andExpect(status().isOk());

        // Validate the FinOperation in the database
        List<FinOperation> finOperations = finOperationRepository.findAll();
        assertThat(finOperations).hasSize(1);
        FinOperation testFinOperation = finOperations.iterator().next();
        assertThat(testFinOperation.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testFinOperation.getValueDate()).isEqualTo(DEFAULT_VALUE_DATE);
        assertThat(testFinOperation.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testFinOperation.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void getAllFinOperations() throws Exception {
        // Initialize the database
        finOperationRepository.saveAndFlush(finOperation);

        // Get all the finOperations
        restFinOperationMockMvc.perform(get("/app/rest/finOperations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(finOperation.getId().intValue()))
                .andExpect(jsonPath("$.[0].creationDate").value(DEFAULT_CREATION_DATE.toString()))
                .andExpect(jsonPath("$.[0].valueDate").value(DEFAULT_VALUE_DATE.toString()))
                .andExpect(jsonPath("$.[0].quantity").value(DEFAULT_QUANTITY.intValue()))
                .andExpect(jsonPath("$.[0].comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getFinOperation() throws Exception {
        // Initialize the database
        finOperationRepository.saveAndFlush(finOperation);

        // Get the finOperation
        restFinOperationMockMvc.perform(get("/app/rest/finOperations/{id}", finOperation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(finOperation.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.valueDate").value(DEFAULT_VALUE_DATE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFinOperation() throws Exception {
        // Get the finOperation
        restFinOperationMockMvc.perform(get("/app/rest/finOperations/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinOperation() throws Exception {
        // Initialize the database
        finOperationRepository.saveAndFlush(finOperation);

        // Update the finOperation
        finOperation.setCreationDate(UPDATED_CREATION_DATE);
        finOperation.setValueDate(UPDATED_VALUE_DATE);
        finOperation.setQuantity(UPDATED_QUANTITY);
        finOperation.setComment(UPDATED_COMMENT);
        restFinOperationMockMvc.perform(post("/app/rest/finOperations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(finOperation)))
                .andExpect(status().isOk());

        // Validate the FinOperation in the database
        List<FinOperation> finOperations = finOperationRepository.findAll();
        assertThat(finOperations).hasSize(1);
        FinOperation testFinOperation = finOperations.iterator().next();
        assertThat(testFinOperation.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testFinOperation.getValueDate()).isEqualTo(UPDATED_VALUE_DATE);
        assertThat(testFinOperation.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testFinOperation.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deleteFinOperation() throws Exception {
        // Initialize the database
        finOperationRepository.saveAndFlush(finOperation);

        // Get the finOperation
        restFinOperationMockMvc.perform(delete("/app/rest/finOperations/{id}", finOperation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FinOperation> finOperations = finOperationRepository.findAll();
        assertThat(finOperations).hasSize(0);
    }
}
