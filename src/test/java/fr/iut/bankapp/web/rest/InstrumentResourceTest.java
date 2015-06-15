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
import fr.iut.bankapp.domain.Instrument;
import fr.iut.bankapp.repository.InstrumentRepository;
import fr.iut.bankapp.web.rest.InstrumentResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InstrumentResource REST controller.
 *
 * @see InstrumentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class InstrumentResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final String DEFAULT_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_TYPE = "UPDATED_TEXT";
    

    @Inject
    private InstrumentRepository instrumentRepository;

    private MockMvc restInstrumentMockMvc;

    private Instrument instrument;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstrumentResource instrumentResource = new InstrumentResource();
        ReflectionTestUtils.setField(instrumentResource, "instrumentRepository", instrumentRepository);
        this.restInstrumentMockMvc = MockMvcBuilders.standaloneSetup(instrumentResource).build();
    }

    @Before
    public void initTest() {
        instrument = new Instrument();
        instrument.setName(DEFAULT_NAME);
        instrument.setType(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createInstrument() throws Exception {
        // Validate the database is empty
        assertThat(instrumentRepository.findAll()).hasSize(0);

        // Create the Instrument
        restInstrumentMockMvc.perform(post("/app/rest/instruments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instrument)))
                .andExpect(status().isOk());

        // Validate the Instrument in the database
        List<Instrument> instruments = instrumentRepository.findAll();
        assertThat(instruments).hasSize(1);
        Instrument testInstrument = instruments.iterator().next();
        assertThat(testInstrument.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstrument.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void getAllInstruments() throws Exception {
        // Initialize the database
        instrumentRepository.saveAndFlush(instrument);

        // Get all the instruments
        restInstrumentMockMvc.perform(get("/app/rest/instruments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(instrument.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getInstrument() throws Exception {
        // Initialize the database
        instrumentRepository.saveAndFlush(instrument);

        // Get the instrument
        restInstrumentMockMvc.perform(get("/app/rest/instruments/{id}", instrument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instrument.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstrument() throws Exception {
        // Get the instrument
        restInstrumentMockMvc.perform(get("/app/rest/instruments/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstrument() throws Exception {
        // Initialize the database
        instrumentRepository.saveAndFlush(instrument);

        // Update the instrument
        instrument.setName(UPDATED_NAME);
        instrument.setType(UPDATED_TYPE);
        restInstrumentMockMvc.perform(post("/app/rest/instruments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instrument)))
                .andExpect(status().isOk());

        // Validate the Instrument in the database
        List<Instrument> instruments = instrumentRepository.findAll();
        assertThat(instruments).hasSize(1);
        Instrument testInstrument = instruments.iterator().next();
        assertThat(testInstrument.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstrument.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteInstrument() throws Exception {
        // Initialize the database
        instrumentRepository.saveAndFlush(instrument);

        // Get the instrument
        restInstrumentMockMvc.perform(delete("/app/rest/instruments/{id}", instrument.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Instrument> instruments = instrumentRepository.findAll();
        assertThat(instruments).hasSize(0);
    }
}
