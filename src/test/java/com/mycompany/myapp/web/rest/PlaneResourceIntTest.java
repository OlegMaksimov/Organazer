package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OrganazerApp;

import com.mycompany.myapp.domain.Plane;
import com.mycompany.myapp.repository.PlaneRepository;
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
 * Test class for the PlaneResource REST controller.
 *
 * @see PlaneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrganazerApp.class)
public class PlaneResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DISCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DISCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PlaneRepository planeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlaneMockMvc;

    private Plane plane;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlaneResource planeResource = new PlaneResource(planeRepository);
        this.restPlaneMockMvc = MockMvcBuilders.standaloneSetup(planeResource)
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
    public static Plane createEntity(EntityManager em) {
        Plane plane = new Plane()
            .dateStart(DEFAULT_DATE_START)
            .dateEnd(DEFAULT_DATE_END)
            .discription(DEFAULT_DISCRIPTION);
        return plane;
    }

    @Before
    public void initTest() {
        plane = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlane() throws Exception {
        int databaseSizeBeforeCreate = planeRepository.findAll().size();

        // Create the Plane
        restPlaneMockMvc.perform(post("/api/planes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plane)))
            .andExpect(status().isCreated());

        // Validate the Plane in the database
        List<Plane> planeList = planeRepository.findAll();
        assertThat(planeList).hasSize(databaseSizeBeforeCreate + 1);
        Plane testPlane = planeList.get(planeList.size() - 1);
        assertThat(testPlane.getDateStart()).isEqualTo(DEFAULT_DATE_START);
        assertThat(testPlane.getDateEnd()).isEqualTo(DEFAULT_DATE_END);
        assertThat(testPlane.getDiscription()).isEqualTo(DEFAULT_DISCRIPTION);
    }

    @Test
    @Transactional
    public void createPlaneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planeRepository.findAll().size();

        // Create the Plane with an existing ID
        plane.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaneMockMvc.perform(post("/api/planes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plane)))
            .andExpect(status().isBadRequest());

        // Validate the Plane in the database
        List<Plane> planeList = planeRepository.findAll();
        assertThat(planeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPlanes() throws Exception {
        // Initialize the database
        planeRepository.saveAndFlush(plane);

        // Get all the planeList
        restPlaneMockMvc.perform(get("/api/planes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plane.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateStart").value(hasItem(sameInstant(DEFAULT_DATE_START))))
            .andExpect(jsonPath("$.[*].dateEnd").value(hasItem(sameInstant(DEFAULT_DATE_END))))
            .andExpect(jsonPath("$.[*].discription").value(hasItem(DEFAULT_DISCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPlane() throws Exception {
        // Initialize the database
        planeRepository.saveAndFlush(plane);

        // Get the plane
        restPlaneMockMvc.perform(get("/api/planes/{id}", plane.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plane.getId().intValue()))
            .andExpect(jsonPath("$.dateStart").value(sameInstant(DEFAULT_DATE_START)))
            .andExpect(jsonPath("$.dateEnd").value(sameInstant(DEFAULT_DATE_END)))
            .andExpect(jsonPath("$.discription").value(DEFAULT_DISCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlane() throws Exception {
        // Get the plane
        restPlaneMockMvc.perform(get("/api/planes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlane() throws Exception {
        // Initialize the database
        planeRepository.saveAndFlush(plane);
        int databaseSizeBeforeUpdate = planeRepository.findAll().size();

        // Update the plane
        Plane updatedPlane = planeRepository.findOne(plane.getId());
        // Disconnect from session so that the updates on updatedPlane are not directly saved in db
        em.detach(updatedPlane);
        updatedPlane
            .dateStart(UPDATED_DATE_START)
            .dateEnd(UPDATED_DATE_END)
            .discription(UPDATED_DISCRIPTION);

        restPlaneMockMvc.perform(put("/api/planes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlane)))
            .andExpect(status().isOk());

        // Validate the Plane in the database
        List<Plane> planeList = planeRepository.findAll();
        assertThat(planeList).hasSize(databaseSizeBeforeUpdate);
        Plane testPlane = planeList.get(planeList.size() - 1);
        assertThat(testPlane.getDateStart()).isEqualTo(UPDATED_DATE_START);
        assertThat(testPlane.getDateEnd()).isEqualTo(UPDATED_DATE_END);
        assertThat(testPlane.getDiscription()).isEqualTo(UPDATED_DISCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPlane() throws Exception {
        int databaseSizeBeforeUpdate = planeRepository.findAll().size();

        // Create the Plane

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlaneMockMvc.perform(put("/api/planes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plane)))
            .andExpect(status().isCreated());

        // Validate the Plane in the database
        List<Plane> planeList = planeRepository.findAll();
        assertThat(planeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlane() throws Exception {
        // Initialize the database
        planeRepository.saveAndFlush(plane);
        int databaseSizeBeforeDelete = planeRepository.findAll().size();

        // Get the plane
        restPlaneMockMvc.perform(delete("/api/planes/{id}", plane.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Plane> planeList = planeRepository.findAll();
        assertThat(planeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plane.class);
        Plane plane1 = new Plane();
        plane1.setId(1L);
        Plane plane2 = new Plane();
        plane2.setId(plane1.getId());
        assertThat(plane1).isEqualTo(plane2);
        plane2.setId(2L);
        assertThat(plane1).isNotEqualTo(plane2);
        plane1.setId(null);
        assertThat(plane1).isNotEqualTo(plane2);
    }
}
