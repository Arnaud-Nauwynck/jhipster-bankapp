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
import fr.iut.bankapp.domain.PortfolioInstrumentPosition;
import fr.iut.bankapp.repository.PortfolioInstrumentPositionRepository;
import fr.iut.bankapp.web.rest.PortfolioInstrumentPositionResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PortfolioInstrumentPositionResource REST controller.
 *
 * @see PortfolioInstrumentPositionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PortfolioInstrumentPositionResourceTest {

    private static final LocalDate DEFAULT_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE = new LocalDate();
    
    private static final BigDecimal DEFAULT_QUANTITY = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_QUANTITY = BigDecimal.ONE;
    

    @Inject
    private PortfolioInstrumentPositionRepository portfolioInstrumentPositionRepository;

    private MockMvc restPortfolioInstrumentPositionMockMvc;

    private PortfolioInstrumentPosition portfolioInstrumentPosition;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PortfolioInstrumentPositionResource portfolioInstrumentPositionResource = new PortfolioInstrumentPositionResource();
        ReflectionTestUtils.setField(portfolioInstrumentPositionResource, "portfolioInstrumentPositionRepository", portfolioInstrumentPositionRepository);
        this.restPortfolioInstrumentPositionMockMvc = MockMvcBuilders.standaloneSetup(portfolioInstrumentPositionResource).build();
    }

    @Before
    public void initTest() {
        portfolioInstrumentPosition = new PortfolioInstrumentPosition();
        portfolioInstrumentPosition.setDate(DEFAULT_DATE);
        portfolioInstrumentPosition.setQuantity(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createPortfolioInstrumentPosition() throws Exception {
        // Validate the database is empty
        assertThat(portfolioInstrumentPositionRepository.findAll()).hasSize(0);

        // Create the PortfolioInstrumentPosition
        restPortfolioInstrumentPositionMockMvc.perform(post("/app/rest/portfolioInstrumentPositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(portfolioInstrumentPosition)))
                .andExpect(status().isOk());

        // Validate the PortfolioInstrumentPosition in the database
        List<PortfolioInstrumentPosition> portfolioInstrumentPositions = portfolioInstrumentPositionRepository.findAll();
        assertThat(portfolioInstrumentPositions).hasSize(1);
        PortfolioInstrumentPosition testPortfolioInstrumentPosition = portfolioInstrumentPositions.iterator().next();
        assertThat(testPortfolioInstrumentPosition.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPortfolioInstrumentPosition.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllPortfolioInstrumentPositions() throws Exception {
        // Initialize the database
        portfolioInstrumentPositionRepository.saveAndFlush(portfolioInstrumentPosition);

        // Get all the portfolioInstrumentPositions
        restPortfolioInstrumentPositionMockMvc.perform(get("/app/rest/portfolioInstrumentPositions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(portfolioInstrumentPosition.getId().intValue()))
                .andExpect(jsonPath("$.[0].date").value(DEFAULT_DATE.toString()))
                .andExpect(jsonPath("$.[0].quantity").value(DEFAULT_QUANTITY.intValue()));
    }

    @Test
    @Transactional
    public void getPortfolioInstrumentPosition() throws Exception {
        // Initialize the database
        portfolioInstrumentPositionRepository.saveAndFlush(portfolioInstrumentPosition);

        // Get the portfolioInstrumentPosition
        restPortfolioInstrumentPositionMockMvc.perform(get("/app/rest/portfolioInstrumentPositions/{id}", portfolioInstrumentPosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(portfolioInstrumentPosition.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPortfolioInstrumentPosition() throws Exception {
        // Get the portfolioInstrumentPosition
        restPortfolioInstrumentPositionMockMvc.perform(get("/app/rest/portfolioInstrumentPositions/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePortfolioInstrumentPosition() throws Exception {
        // Initialize the database
        portfolioInstrumentPositionRepository.saveAndFlush(portfolioInstrumentPosition);

        // Update the portfolioInstrumentPosition
        portfolioInstrumentPosition.setDate(UPDATED_DATE);
        portfolioInstrumentPosition.setQuantity(UPDATED_QUANTITY);
        restPortfolioInstrumentPositionMockMvc.perform(post("/app/rest/portfolioInstrumentPositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(portfolioInstrumentPosition)))
                .andExpect(status().isOk());

        // Validate the PortfolioInstrumentPosition in the database
        List<PortfolioInstrumentPosition> portfolioInstrumentPositions = portfolioInstrumentPositionRepository.findAll();
        assertThat(portfolioInstrumentPositions).hasSize(1);
        PortfolioInstrumentPosition testPortfolioInstrumentPosition = portfolioInstrumentPositions.iterator().next();
        assertThat(testPortfolioInstrumentPosition.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPortfolioInstrumentPosition.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void deletePortfolioInstrumentPosition() throws Exception {
        // Initialize the database
        portfolioInstrumentPositionRepository.saveAndFlush(portfolioInstrumentPosition);

        // Get the portfolioInstrumentPosition
        restPortfolioInstrumentPositionMockMvc.perform(delete("/app/rest/portfolioInstrumentPositions/{id}", portfolioInstrumentPosition.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PortfolioInstrumentPosition> portfolioInstrumentPositions = portfolioInstrumentPositionRepository.findAll();
        assertThat(portfolioInstrumentPositions).hasSize(0);
    }
}
