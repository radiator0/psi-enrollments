package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.EnrollmentDate;
import com.psi.repository.EnrollmentDateRepository;
import com.psi.service.EnrollmentDateService;
import com.psi.service.dto.EnrollmentDateDTO;
import com.psi.service.mapper.EnrollmentDateMapper;

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
 * Integration tests for the {@link EnrollmentDateResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EnrollmentDateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PRE_ENROLLMENT = false;
    private static final Boolean UPDATED_IS_PRE_ENROLLMENT = true;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EnrollmentDateRepository enrollmentDateRepository;

    @Autowired
    private EnrollmentDateMapper enrollmentDateMapper;

    @Autowired
    private EnrollmentDateService enrollmentDateService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnrollmentDateMockMvc;

    private EnrollmentDate enrollmentDate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnrollmentDate createEntity(EntityManager em) {
        EnrollmentDate enrollmentDate = new EnrollmentDate()
            .name(DEFAULT_NAME)
            .isPreEnrollment(DEFAULT_IS_PRE_ENROLLMENT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return enrollmentDate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnrollmentDate createUpdatedEntity(EntityManager em) {
        EnrollmentDate enrollmentDate = new EnrollmentDate()
            .name(UPDATED_NAME)
            .isPreEnrollment(UPDATED_IS_PRE_ENROLLMENT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return enrollmentDate;
    }

    @BeforeEach
    public void initTest() {
        enrollmentDate = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnrollmentDate() throws Exception {
        int databaseSizeBeforeCreate = enrollmentDateRepository.findAll().size();
        // Create the EnrollmentDate
        EnrollmentDateDTO enrollmentDateDTO = enrollmentDateMapper.toDto(enrollmentDate);
        restEnrollmentDateMockMvc.perform(post("/api/enrollment-dates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentDateDTO)))
            .andExpect(status().isCreated());

        // Validate the EnrollmentDate in the database
        List<EnrollmentDate> enrollmentDateList = enrollmentDateRepository.findAll();
        assertThat(enrollmentDateList).hasSize(databaseSizeBeforeCreate + 1);
        EnrollmentDate testEnrollmentDate = enrollmentDateList.get(enrollmentDateList.size() - 1);
        assertThat(testEnrollmentDate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEnrollmentDate.isIsPreEnrollment()).isEqualTo(DEFAULT_IS_PRE_ENROLLMENT);
        assertThat(testEnrollmentDate.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEnrollmentDate.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createEnrollmentDateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enrollmentDateRepository.findAll().size();

        // Create the EnrollmentDate with an existing ID
        enrollmentDate.setId(1L);
        EnrollmentDateDTO enrollmentDateDTO = enrollmentDateMapper.toDto(enrollmentDate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnrollmentDateMockMvc.perform(post("/api/enrollment-dates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentDateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnrollmentDate in the database
        List<EnrollmentDate> enrollmentDateList = enrollmentDateRepository.findAll();
        assertThat(enrollmentDateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrollmentDateRepository.findAll().size();
        // set the field null
        enrollmentDate.setName(null);

        // Create the EnrollmentDate, which fails.
        EnrollmentDateDTO enrollmentDateDTO = enrollmentDateMapper.toDto(enrollmentDate);


        restEnrollmentDateMockMvc.perform(post("/api/enrollment-dates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentDateDTO)))
            .andExpect(status().isBadRequest());

        List<EnrollmentDate> enrollmentDateList = enrollmentDateRepository.findAll();
        assertThat(enrollmentDateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsPreEnrollmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrollmentDateRepository.findAll().size();
        // set the field null
        enrollmentDate.setIsPreEnrollment(null);

        // Create the EnrollmentDate, which fails.
        EnrollmentDateDTO enrollmentDateDTO = enrollmentDateMapper.toDto(enrollmentDate);


        restEnrollmentDateMockMvc.perform(post("/api/enrollment-dates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentDateDTO)))
            .andExpect(status().isBadRequest());

        List<EnrollmentDate> enrollmentDateList = enrollmentDateRepository.findAll();
        assertThat(enrollmentDateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrollmentDateRepository.findAll().size();
        // set the field null
        enrollmentDate.setStartDate(null);

        // Create the EnrollmentDate, which fails.
        EnrollmentDateDTO enrollmentDateDTO = enrollmentDateMapper.toDto(enrollmentDate);


        restEnrollmentDateMockMvc.perform(post("/api/enrollment-dates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentDateDTO)))
            .andExpect(status().isBadRequest());

        List<EnrollmentDate> enrollmentDateList = enrollmentDateRepository.findAll();
        assertThat(enrollmentDateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrollmentDateRepository.findAll().size();
        // set the field null
        enrollmentDate.setEndDate(null);

        // Create the EnrollmentDate, which fails.
        EnrollmentDateDTO enrollmentDateDTO = enrollmentDateMapper.toDto(enrollmentDate);


        restEnrollmentDateMockMvc.perform(post("/api/enrollment-dates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentDateDTO)))
            .andExpect(status().isBadRequest());

        List<EnrollmentDate> enrollmentDateList = enrollmentDateRepository.findAll();
        assertThat(enrollmentDateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnrollmentDates() throws Exception {
        // Initialize the database
        enrollmentDateRepository.saveAndFlush(enrollmentDate);

        // Get all the enrollmentDateList
        restEnrollmentDateMockMvc.perform(get("/api/enrollment-dates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enrollmentDate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isPreEnrollment").value(hasItem(DEFAULT_IS_PRE_ENROLLMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEnrollmentDate() throws Exception {
        // Initialize the database
        enrollmentDateRepository.saveAndFlush(enrollmentDate);

        // Get the enrollmentDate
        restEnrollmentDateMockMvc.perform(get("/api/enrollment-dates/{id}", enrollmentDate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enrollmentDate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isPreEnrollment").value(DEFAULT_IS_PRE_ENROLLMENT.booleanValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEnrollmentDate() throws Exception {
        // Get the enrollmentDate
        restEnrollmentDateMockMvc.perform(get("/api/enrollment-dates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnrollmentDate() throws Exception {
        // Initialize the database
        enrollmentDateRepository.saveAndFlush(enrollmentDate);

        int databaseSizeBeforeUpdate = enrollmentDateRepository.findAll().size();

        // Update the enrollmentDate
        EnrollmentDate updatedEnrollmentDate = enrollmentDateRepository.findById(enrollmentDate.getId()).get();
        // Disconnect from session so that the updates on updatedEnrollmentDate are not directly saved in db
        em.detach(updatedEnrollmentDate);
        updatedEnrollmentDate
            .name(UPDATED_NAME)
            .isPreEnrollment(UPDATED_IS_PRE_ENROLLMENT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        EnrollmentDateDTO enrollmentDateDTO = enrollmentDateMapper.toDto(updatedEnrollmentDate);

        restEnrollmentDateMockMvc.perform(put("/api/enrollment-dates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentDateDTO)))
            .andExpect(status().isOk());

        // Validate the EnrollmentDate in the database
        List<EnrollmentDate> enrollmentDateList = enrollmentDateRepository.findAll();
        assertThat(enrollmentDateList).hasSize(databaseSizeBeforeUpdate);
        EnrollmentDate testEnrollmentDate = enrollmentDateList.get(enrollmentDateList.size() - 1);
        assertThat(testEnrollmentDate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEnrollmentDate.isIsPreEnrollment()).isEqualTo(UPDATED_IS_PRE_ENROLLMENT);
        assertThat(testEnrollmentDate.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEnrollmentDate.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEnrollmentDate() throws Exception {
        int databaseSizeBeforeUpdate = enrollmentDateRepository.findAll().size();

        // Create the EnrollmentDate
        EnrollmentDateDTO enrollmentDateDTO = enrollmentDateMapper.toDto(enrollmentDate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnrollmentDateMockMvc.perform(put("/api/enrollment-dates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentDateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnrollmentDate in the database
        List<EnrollmentDate> enrollmentDateList = enrollmentDateRepository.findAll();
        assertThat(enrollmentDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnrollmentDate() throws Exception {
        // Initialize the database
        enrollmentDateRepository.saveAndFlush(enrollmentDate);

        int databaseSizeBeforeDelete = enrollmentDateRepository.findAll().size();

        // Delete the enrollmentDate
        restEnrollmentDateMockMvc.perform(delete("/api/enrollment-dates/{id}", enrollmentDate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EnrollmentDate> enrollmentDateList = enrollmentDateRepository.findAll();
        assertThat(enrollmentDateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
