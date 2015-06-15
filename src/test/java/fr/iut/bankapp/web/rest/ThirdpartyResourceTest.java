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
import fr.iut.bankapp.domain.Thirdparty;
import fr.iut.bankapp.repository.ThirdpartyRepository;
import fr.iut.bankapp.web.rest.ThirdpartyResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ThirdpartyResource REST controller.
 *
 * @see ThirdpartyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ThirdpartyResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final String DEFAULT_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_TYPE = "UPDATED_TEXT";
    
    private static final String DEFAULT_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS = "UPDATED_TEXT";
    
    private static final String DEFAULT_ZIP_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_ZIP_CODE = "UPDATED_TEXT";
    

    @Inject
    private ThirdpartyRepository thirdpartyRepository;

    private MockMvc restThirdpartyMockMvc;

    private Thirdparty thirdparty;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThirdpartyResource thirdpartyResource = new ThirdpartyResource();
        ReflectionTestUtils.setField(thirdpartyResource, "thirdpartyRepository", thirdpartyRepository);
        this.restThirdpartyMockMvc = MockMvcBuilders.standaloneSetup(thirdpartyResource).build();
    }

    @Before
    public void initTest() {
        thirdparty = new Thirdparty();
        thirdparty.setName(DEFAULT_NAME);
        thirdparty.setType(DEFAULT_TYPE);
        thirdparty.setAddress(DEFAULT_ADDRESS);
        thirdparty.setZipCode(DEFAULT_ZIP_CODE);
    }

    @Test
    @Transactional
    public void createThirdparty() throws Exception {
        // Validate the database is empty
        assertThat(thirdpartyRepository.findAll()).hasSize(0);

        // Create the Thirdparty
        restThirdpartyMockMvc.perform(post("/app/rest/thirdpartys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thirdparty)))
                .andExpect(status().isOk());

        // Validate the Thirdparty in the database
        List<Thirdparty> thirdpartys = thirdpartyRepository.findAll();
        assertThat(thirdpartys).hasSize(1);
        Thirdparty testThirdparty = thirdpartys.iterator().next();
        assertThat(testThirdparty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testThirdparty.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testThirdparty.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testThirdparty.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
    }

    @Test
    @Transactional
    public void getAllThirdpartys() throws Exception {
        // Initialize the database
        thirdpartyRepository.saveAndFlush(thirdparty);

        // Get all the thirdpartys
        restThirdpartyMockMvc.perform(get("/app/rest/thirdpartys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(thirdparty.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].type").value(DEFAULT_TYPE.toString()))
                .andExpect(jsonPath("$.[0].address").value(DEFAULT_ADDRESS.toString()))
                .andExpect(jsonPath("$.[0].zipCode").value(DEFAULT_ZIP_CODE.toString()));
    }

    @Test
    @Transactional
    public void getThirdparty() throws Exception {
        // Initialize the database
        thirdpartyRepository.saveAndFlush(thirdparty);

        // Get the thirdparty
        restThirdpartyMockMvc.perform(get("/app/rest/thirdpartys/{id}", thirdparty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(thirdparty.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingThirdparty() throws Exception {
        // Get the thirdparty
        restThirdpartyMockMvc.perform(get("/app/rest/thirdpartys/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThirdparty() throws Exception {
        // Initialize the database
        thirdpartyRepository.saveAndFlush(thirdparty);

        // Update the thirdparty
        thirdparty.setName(UPDATED_NAME);
        thirdparty.setType(UPDATED_TYPE);
        thirdparty.setAddress(UPDATED_ADDRESS);
        thirdparty.setZipCode(UPDATED_ZIP_CODE);
        restThirdpartyMockMvc.perform(post("/app/rest/thirdpartys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(thirdparty)))
                .andExpect(status().isOk());

        // Validate the Thirdparty in the database
        List<Thirdparty> thirdpartys = thirdpartyRepository.findAll();
        assertThat(thirdpartys).hasSize(1);
        Thirdparty testThirdparty = thirdpartys.iterator().next();
        assertThat(testThirdparty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testThirdparty.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testThirdparty.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testThirdparty.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void deleteThirdparty() throws Exception {
        // Initialize the database
        thirdpartyRepository.saveAndFlush(thirdparty);

        // Get the thirdparty
        restThirdpartyMockMvc.perform(delete("/app/rest/thirdpartys/{id}", thirdparty.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Thirdparty> thirdpartys = thirdpartyRepository.findAll();
        assertThat(thirdpartys).hasSize(0);
    }
}
