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

import java.util.List;

import fr.iut.bankapp.Application;
import fr.iut.bankapp.domain.Portfolio;
import fr.iut.bankapp.repository.PortfolioRepository;
import fr.iut.bankapp.web.rest.PortfolioResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PortfolioResource REST controller.
 *
 * @see PortfolioResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PortfolioResourceTest {


    @Inject
    private PortfolioRepository portfolioRepository;

    private MockMvc restPortfolioMockMvc;

    private Portfolio portfolio;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PortfolioResource portfolioResource = new PortfolioResource();
        ReflectionTestUtils.setField(portfolioResource, "portfolioRepository", portfolioRepository);
        this.restPortfolioMockMvc = MockMvcBuilders.standaloneSetup(portfolioResource).build();
    }

    @Before
    public void initTest() {
        portfolio = new Portfolio();
    }

    @Test
    @Transactional
    public void createPortfolio() throws Exception {
        // Validate the database is empty
        assertThat(portfolioRepository.findAll()).hasSize(0);

        // Create the Portfolio
        restPortfolioMockMvc.perform(post("/app/rest/portfolios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(portfolio)))
                .andExpect(status().isOk());

        // Validate the Portfolio in the database
        List<Portfolio> portfolios = portfolioRepository.findAll();
        assertThat(portfolios).hasSize(1);
        Portfolio testPortfolio = portfolios.iterator().next();
    }

    @Test
    @Transactional
    public void getAllPortfolios() throws Exception {
        // Initialize the database
        portfolioRepository.saveAndFlush(portfolio);

        // Get all the portfolios
        restPortfolioMockMvc.perform(get("/app/rest/portfolios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(portfolio.getId().intValue()));
    }

    @Test
    @Transactional
    public void getPortfolio() throws Exception {
        // Initialize the database
        portfolioRepository.saveAndFlush(portfolio);

        // Get the portfolio
        restPortfolioMockMvc.perform(get("/app/rest/portfolios/{id}", portfolio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(portfolio.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPortfolio() throws Exception {
        // Get the portfolio
        restPortfolioMockMvc.perform(get("/app/rest/portfolios/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePortfolio() throws Exception {
        // Initialize the database
        portfolioRepository.saveAndFlush(portfolio);

        // Update the portfolio
        restPortfolioMockMvc.perform(post("/app/rest/portfolios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(portfolio)))
                .andExpect(status().isOk());

        // Validate the Portfolio in the database
        List<Portfolio> portfolios = portfolioRepository.findAll();
        assertThat(portfolios).hasSize(1);
        Portfolio testPortfolio = portfolios.iterator().next();
    }

    @Test
    @Transactional
    public void deletePortfolio() throws Exception {
        // Initialize the database
        portfolioRepository.saveAndFlush(portfolio);

        // Get the portfolio
        restPortfolioMockMvc.perform(delete("/app/rest/portfolios/{id}", portfolio.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Portfolio> portfolios = portfolioRepository.findAll();
        assertThat(portfolios).hasSize(0);
    }
}
