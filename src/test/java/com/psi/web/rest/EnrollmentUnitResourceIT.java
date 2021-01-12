package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.EnrollmentUnit;
import com.psi.repository.EnrollmentUnitRepository;
import com.psi.service.EnrollmentUnitService;
import com.psi.service.dto.EnrollmentUnitDTO;
import com.psi.service.mapper.EnrollmentUnitMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EnrollmentUnitResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EnrollmentUnitResourceIT {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EnrollmentUnitRepository enrollmentUnitRepository;

    @Autowired
    private EnrollmentUnitMapper enrollmentUnitMapper;

    @Autowired
    private EnrollmentUnitService enrollmentUnitService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnrollmentUnitMockMvc;

    private EnrollmentUnit enrollmentUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnrollmentUnit createEntity(EntityManager em) {
        EnrollmentUnit enrollmentUnit = new EnrollmentUnit()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return enrollmentUnit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnrollmentUnit createUpdatedEntity(EntityManager em) {
        EnrollmentUnit enrollmentUnit = new EnrollmentUnit()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return enrollmentUnit;
    }

    @BeforeEach
    public void initTest() {
        enrollmentUnit = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnrollmentUnit() throws Exception {
        int databaseSizeBeforeCreate = enrollmentUnitRepository.findAll().size();
        // Create the EnrollmentUnit
        EnrollmentUnitDTO enrollmentUnitDTO = enrollmentUnitMapper.toDto(enrollmentUnit);
        restEnrollmentUnitMockMvc.perform(post("/api/enrollment-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the EnrollmentUnit in the database
        List<EnrollmentUnit> enrollmentUnitList = enrollmentUnitRepository.findAll();
        assertThat(enrollmentUnitList).hasSize(databaseSizeBeforeCreate + 1);
        EnrollmentUnit testEnrollmentUnit = enrollmentUnitList.get(enrollmentUnitList.size() - 1);
        assertThat(testEnrollmentUnit.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEnrollmentUnit.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createEnrollmentUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enrollmentUnitRepository.findAll().size();

        // Create the EnrollmentUnit with an existing ID
        enrollmentUnit.setId(1L);
        EnrollmentUnitDTO enrollmentUnitDTO = enrollmentUnitMapper.toDto(enrollmentUnit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnrollmentUnitMockMvc.perform(post("/api/enrollment-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnrollmentUnit in the database
        List<EnrollmentUnit> enrollmentUnitList = enrollmentUnitRepository.findAll();
        assertThat(enrollmentUnitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrollmentUnitRepository.findAll().size();
        // set the field null
        enrollmentUnit.setStartDate(null);

        // Create the EnrollmentUnit, which fails.
        EnrollmentUnitDTO enrollmentUnitDTO = enrollmentUnitMapper.toDto(enrollmentUnit);


        restEnrollmentUnitMockMvc.perform(post("/api/enrollment-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentUnitDTO)))
            .andExpect(status().isBadRequest());

        List<EnrollmentUnit> enrollmentUnitList = enrollmentUnitRepository.findAll();
        assertThat(enrollmentUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrollmentUnitRepository.findAll().size();
        // set the field null
        enrollmentUnit.setEndDate(null);

        // Create the EnrollmentUnit, which fails.
        EnrollmentUnitDTO enrollmentUnitDTO = enrollmentUnitMapper.toDto(enrollmentUnit);


        restEnrollmentUnitMockMvc.perform(post("/api/enrollment-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentUnitDTO)))
            .andExpect(status().isBadRequest());

        List<EnrollmentUnit> enrollmentUnitList = enrollmentUnitRepository.findAll();
        assertThat(enrollmentUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnrollmentUnits() throws Exception {
        // Initialize the database
        enrollmentUnitRepository.saveAndFlush(enrollmentUnit);

        // Get all the enrollmentUnitList
        restEnrollmentUnitMockMvc.perform(get("/api/enrollment-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enrollmentUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEnrollmentUnit() throws Exception {
        // Initialize the database
        enrollmentUnitRepository.saveAndFlush(enrollmentUnit);

        // Get the enrollmentUnit
        restEnrollmentUnitMockMvc.perform(get("/api/enrollment-units/{id}", enrollmentUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enrollmentUnit.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEnrollmentUnit() throws Exception {
        // Get the enrollmentUnit
        restEnrollmentUnitMockMvc.perform(get("/api/enrollment-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnrollmentUnit() throws Exception {
        // Initialize the database
        enrollmentUnitRepository.saveAndFlush(enrollmentUnit);

        int databaseSizeBeforeUpdate = enrollmentUnitRepository.findAll().size();

        // Update the enrollmentUnit
        EnrollmentUnit updatedEnrollmentUnit = enrollmentUnitRepository.findById(enrollmentUnit.getId()).get();
        // Disconnect from session so that the updates on updatedEnrollmentUnit are not directly saved in db
        em.detach(updatedEnrollmentUnit);
        updatedEnrollmentUnit
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        EnrollmentUnitDTO enrollmentUnitDTO = enrollmentUnitMapper.toDto(updatedEnrollmentUnit);

        restEnrollmentUnitMockMvc.perform(put("/api/enrollment-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentUnitDTO)))
            .andExpect(status().isOk());

        // Validate the EnrollmentUnit in the database
        List<EnrollmentUnit> enrollmentUnitList = enrollmentUnitRepository.findAll();
        assertThat(enrollmentUnitList).hasSize(databaseSizeBeforeUpdate);
        EnrollmentUnit testEnrollmentUnit = enrollmentUnitList.get(enrollmentUnitList.size() - 1);
        assertThat(testEnrollmentUnit.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEnrollmentUnit.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEnrollmentUnit() throws Exception {
        int databaseSizeBeforeUpdate = enrollmentUnitRepository.findAll().size();

        // Create the EnrollmentUnit
        EnrollmentUnitDTO enrollmentUnitDTO = enrollmentUnitMapper.toDto(enrollmentUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnrollmentUnitMockMvc.perform(put("/api/enrollment-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnrollmentUnit in the database
        List<EnrollmentUnit> enrollmentUnitList = enrollmentUnitRepository.findAll();
        assertThat(enrollmentUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnrollmentUnit() throws Exception {
        // Initialize the database
        enrollmentUnitRepository.saveAndFlush(enrollmentUnit);

        int databaseSizeBeforeDelete = enrollmentUnitRepository.findAll().size();

        // Delete the enrollmentUnit
        restEnrollmentUnitMockMvc.perform(delete("/api/enrollment-units/{id}", enrollmentUnit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EnrollmentUnit> enrollmentUnitList = enrollmentUnitRepository.findAll();
        assertThat(enrollmentUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
