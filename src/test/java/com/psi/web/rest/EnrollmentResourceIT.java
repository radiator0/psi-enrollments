package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.Enrollment;
import com.psi.repository.EnrollmentRepository;
import com.psi.service.EnrollmentService;
import com.psi.service.dto.EnrollmentDTO;
import com.psi.service.mapper.EnrollmentMapper;
import com.psi.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.psi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EnrollmentResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
public class EnrollmentResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_ADMINISTRATIVE = false;
    private static final Boolean UPDATED_IS_ADMINISTRATIVE = true;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private EnrollmentMapper enrollmentMapper;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEnrollmentMockMvc;

    private Enrollment enrollment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnrollmentResource enrollmentResource = new EnrollmentResource(enrollmentService);
        this.restEnrollmentMockMvc = MockMvcBuilders.standaloneSetup(enrollmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enrollment createEntity(EntityManager em) {
        Enrollment enrollment = new Enrollment()
            .date(DEFAULT_DATE)
            .isAdministrative(DEFAULT_IS_ADMINISTRATIVE);
        return enrollment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enrollment createUpdatedEntity(EntityManager em) {
        Enrollment enrollment = new Enrollment()
            .date(UPDATED_DATE)
            .isAdministrative(UPDATED_IS_ADMINISTRATIVE);
        return enrollment;
    }

    @BeforeEach
    public void initTest() {
        enrollment = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnrollment() throws Exception {
        int databaseSizeBeforeCreate = enrollmentRepository.findAll().size();

        // Create the Enrollment
        EnrollmentDTO enrollmentDTO = enrollmentMapper.toDto(enrollment);
        restEnrollmentMockMvc.perform(post("/api/enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Enrollment in the database
        List<Enrollment> enrollmentList = enrollmentRepository.findAll();
        assertThat(enrollmentList).hasSize(databaseSizeBeforeCreate + 1);
        Enrollment testEnrollment = enrollmentList.get(enrollmentList.size() - 1);
        assertThat(testEnrollment.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEnrollment.isIsAdministrative()).isEqualTo(DEFAULT_IS_ADMINISTRATIVE);
    }

    @Test
    @Transactional
    public void createEnrollmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enrollmentRepository.findAll().size();

        // Create the Enrollment with an existing ID
        enrollment.setId(1L);
        EnrollmentDTO enrollmentDTO = enrollmentMapper.toDto(enrollment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnrollmentMockMvc.perform(post("/api/enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enrollment in the database
        List<Enrollment> enrollmentList = enrollmentRepository.findAll();
        assertThat(enrollmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrollmentRepository.findAll().size();
        // set the field null
        enrollment.setDate(null);

        // Create the Enrollment, which fails.
        EnrollmentDTO enrollmentDTO = enrollmentMapper.toDto(enrollment);

        restEnrollmentMockMvc.perform(post("/api/enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentDTO)))
            .andExpect(status().isBadRequest());

        List<Enrollment> enrollmentList = enrollmentRepository.findAll();
        assertThat(enrollmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsAdministrativeIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrollmentRepository.findAll().size();
        // set the field null
        enrollment.setIsAdministrative(null);

        // Create the Enrollment, which fails.
        EnrollmentDTO enrollmentDTO = enrollmentMapper.toDto(enrollment);

        restEnrollmentMockMvc.perform(post("/api/enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentDTO)))
            .andExpect(status().isBadRequest());

        List<Enrollment> enrollmentList = enrollmentRepository.findAll();
        assertThat(enrollmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnrollments() throws Exception {
        // Initialize the database
        enrollmentRepository.saveAndFlush(enrollment);

        // Get all the enrollmentList
        restEnrollmentMockMvc.perform(get("/api/enrollments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enrollment.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].isAdministrative").value(hasItem(DEFAULT_IS_ADMINISTRATIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getEnrollment() throws Exception {
        // Initialize the database
        enrollmentRepository.saveAndFlush(enrollment);

        // Get the enrollment
        restEnrollmentMockMvc.perform(get("/api/enrollments/{id}", enrollment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enrollment.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.isAdministrative").value(DEFAULT_IS_ADMINISTRATIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEnrollment() throws Exception {
        // Get the enrollment
        restEnrollmentMockMvc.perform(get("/api/enrollments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnrollment() throws Exception {
        // Initialize the database
        enrollmentRepository.saveAndFlush(enrollment);

        int databaseSizeBeforeUpdate = enrollmentRepository.findAll().size();

        // Update the enrollment
        Enrollment updatedEnrollment = enrollmentRepository.findById(enrollment.getId()).get();
        // Disconnect from session so that the updates on updatedEnrollment are not directly saved in db
        em.detach(updatedEnrollment);
        updatedEnrollment
            .date(UPDATED_DATE)
            .isAdministrative(UPDATED_IS_ADMINISTRATIVE);
        EnrollmentDTO enrollmentDTO = enrollmentMapper.toDto(updatedEnrollment);

        restEnrollmentMockMvc.perform(put("/api/enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentDTO)))
            .andExpect(status().isOk());

        // Validate the Enrollment in the database
        List<Enrollment> enrollmentList = enrollmentRepository.findAll();
        assertThat(enrollmentList).hasSize(databaseSizeBeforeUpdate);
        Enrollment testEnrollment = enrollmentList.get(enrollmentList.size() - 1);
        assertThat(testEnrollment.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEnrollment.isIsAdministrative()).isEqualTo(UPDATED_IS_ADMINISTRATIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingEnrollment() throws Exception {
        int databaseSizeBeforeUpdate = enrollmentRepository.findAll().size();

        // Create the Enrollment
        EnrollmentDTO enrollmentDTO = enrollmentMapper.toDto(enrollment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnrollmentMockMvc.perform(put("/api/enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enrollment in the database
        List<Enrollment> enrollmentList = enrollmentRepository.findAll();
        assertThat(enrollmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnrollment() throws Exception {
        // Initialize the database
        enrollmentRepository.saveAndFlush(enrollment);

        int databaseSizeBeforeDelete = enrollmentRepository.findAll().size();

        // Delete the enrollment
        restEnrollmentMockMvc.perform(delete("/api/enrollments/{id}", enrollment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Enrollment> enrollmentList = enrollmentRepository.findAll();
        assertThat(enrollmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enrollment.class);
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setId(1L);
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setId(enrollment1.getId());
        assertThat(enrollment1).isEqualTo(enrollment2);
        enrollment2.setId(2L);
        assertThat(enrollment1).isNotEqualTo(enrollment2);
        enrollment1.setId(null);
        assertThat(enrollment1).isNotEqualTo(enrollment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrollmentDTO.class);
        EnrollmentDTO enrollmentDTO1 = new EnrollmentDTO();
        enrollmentDTO1.setId(1L);
        EnrollmentDTO enrollmentDTO2 = new EnrollmentDTO();
        assertThat(enrollmentDTO1).isNotEqualTo(enrollmentDTO2);
        enrollmentDTO2.setId(enrollmentDTO1.getId());
        assertThat(enrollmentDTO1).isEqualTo(enrollmentDTO2);
        enrollmentDTO2.setId(2L);
        assertThat(enrollmentDTO1).isNotEqualTo(enrollmentDTO2);
        enrollmentDTO1.setId(null);
        assertThat(enrollmentDTO1).isNotEqualTo(enrollmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(enrollmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(enrollmentMapper.fromId(null)).isNull();
    }
}
