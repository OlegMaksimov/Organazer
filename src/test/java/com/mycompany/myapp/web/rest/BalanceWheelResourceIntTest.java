package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OrganazerApp;

import com.mycompany.myapp.domain.BalanceWheel;
import com.mycompany.myapp.repository.BalanceWheelRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BalanceWheelResource REST controller.
 *
 * @see BalanceWheelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrganazerApp.class)
public class BalanceWheelResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BalanceWheelRepository balanceWheelRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBalanceWheelMockMvc;

    private BalanceWheel balanceWheel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BalanceWheelResource balanceWheelResource = new BalanceWheelResource(balanceWheelRepository);
        this.restBalanceWheelMockMvc = MockMvcBuilders.standaloneSetup(balanceWheelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BalanceWheel createEntity(EntityManager em) {
        BalanceWheel balanceWheel = new BalanceWheel()
            .date(DEFAULT_DATE);
        return balanceWheel;
    }

    @Before
    public void initTest() {
        balanceWheel = createEntity(em);
    }

    @Test
    @Transactional
    public void createBalanceWheel() throws Exception {
        int databaseSizeBeforeCreate = balanceWheelRepository.findAll().size();

        // Create the BalanceWheel
        restBalanceWheelMockMvc.perform(post("/api/balance-wheels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceWheel)))
            .andExpect(status().isCreated());

        // Validate the BalanceWheel in the database
        List<BalanceWheel> balanceWheelList = balanceWheelRepository.findAll();
        assertThat(balanceWheelList).hasSize(databaseSizeBeforeCreate + 1);
        BalanceWheel testBalanceWheel = balanceWheelList.get(balanceWheelList.size() - 1);
        assertThat(testBalanceWheel.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createBalanceWheelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = balanceWheelRepository.findAll().size();

        // Create the BalanceWheel with an existing ID
        balanceWheel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBalanceWheelMockMvc.perform(post("/api/balance-wheels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceWheel)))
            .andExpect(status().isBadRequest());

        // Validate the BalanceWheel in the database
        List<BalanceWheel> balanceWheelList = balanceWheelRepository.findAll();
        assertThat(balanceWheelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBalanceWheels() throws Exception {
        // Initialize the database
        balanceWheelRepository.saveAndFlush(balanceWheel);

        // Get all the balanceWheelList
        restBalanceWheelMockMvc.perform(get("/api/balance-wheels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(balanceWheel.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getBalanceWheel() throws Exception {
        // Initialize the database
        balanceWheelRepository.saveAndFlush(balanceWheel);

        // Get the balanceWheel
        restBalanceWheelMockMvc.perform(get("/api/balance-wheels/{id}", balanceWheel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(balanceWheel.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingBalanceWheel() throws Exception {
        // Get the balanceWheel
        restBalanceWheelMockMvc.perform(get("/api/balance-wheels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBalanceWheel() throws Exception {
        // Initialize the database
        balanceWheelRepository.saveAndFlush(balanceWheel);
        int databaseSizeBeforeUpdate = balanceWheelRepository.findAll().size();

        // Update the balanceWheel
        BalanceWheel updatedBalanceWheel = balanceWheelRepository.findOne(balanceWheel.getId());
        // Disconnect from session so that the updates on updatedBalanceWheel are not directly saved in db
        em.detach(updatedBalanceWheel);
        updatedBalanceWheel
            .date(UPDATED_DATE);

        restBalanceWheelMockMvc.perform(put("/api/balance-wheels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBalanceWheel)))
            .andExpect(status().isOk());

        // Validate the BalanceWheel in the database
        List<BalanceWheel> balanceWheelList = balanceWheelRepository.findAll();
        assertThat(balanceWheelList).hasSize(databaseSizeBeforeUpdate);
        BalanceWheel testBalanceWheel = balanceWheelList.get(balanceWheelList.size() - 1);
        assertThat(testBalanceWheel.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingBalanceWheel() throws Exception {
        int databaseSizeBeforeUpdate = balanceWheelRepository.findAll().size();

        // Create the BalanceWheel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBalanceWheelMockMvc.perform(put("/api/balance-wheels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(balanceWheel)))
            .andExpect(status().isCreated());

        // Validate the BalanceWheel in the database
        List<BalanceWheel> balanceWheelList = balanceWheelRepository.findAll();
        assertThat(balanceWheelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBalanceWheel() throws Exception {
        // Initialize the database
        balanceWheelRepository.saveAndFlush(balanceWheel);
        int databaseSizeBeforeDelete = balanceWheelRepository.findAll().size();

        // Get the balanceWheel
        restBalanceWheelMockMvc.perform(delete("/api/balance-wheels/{id}", balanceWheel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BalanceWheel> balanceWheelList = balanceWheelRepository.findAll();
        assertThat(balanceWheelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BalanceWheel.class);
        BalanceWheel balanceWheel1 = new BalanceWheel();
        balanceWheel1.setId(1L);
        BalanceWheel balanceWheel2 = new BalanceWheel();
        balanceWheel2.setId(balanceWheel1.getId());
        assertThat(balanceWheel1).isEqualTo(balanceWheel2);
        balanceWheel2.setId(2L);
        assertThat(balanceWheel1).isNotEqualTo(balanceWheel2);
        balanceWheel1.setId(null);
        assertThat(balanceWheel1).isNotEqualTo(balanceWheel2);
    }
}
