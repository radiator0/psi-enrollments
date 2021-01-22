package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.Enrollment;
import com.psi.domain.Student;
import com.psi.domain.ClassGroup;
import com.psi.repository.EnrollmentRepository;
import com.psi.service.EnrollmentService;
import com.psi.service.dto.EnrollmentDTO;
import com.psi.service.mapper.EnrollmentMapper;

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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EnrollmentResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EnrollmentResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_ADMINISTRATIVE = false;
    private static final Boolean UPDATED_IS_ADMINISTRATIVE = true;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private EnrollmentMapper enrollmentMapper;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnrollmentMockMvc;

    private Enrollment enrollment;

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
        // Add required entity
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            student = StudentResourceIT.createEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        enrollment.setStudent(student);
        // Add required entity
        ClassGroup classGroup;
        if (TestUtil.findAll(em, ClassGroup.class).isEmpty()) {
            classGroup = ClassGroupResourceIT.createEntity(em);
            em.persist(classGroup);
            em.flush();
        } else {
            classGroup = TestUtil.findAll(em, ClassGroup.class).get(0);
        }
        enrollment.setClassGroup(classGroup);
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
        // Add required entity
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            student = StudentResourceIT.createUpdatedEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        enrollment.setStudent(student);
        // Add required entity
        ClassGroup classGroup;
        if (TestUtil.findAll(em, ClassGroup.class).isEmpty()) {
            classGroup = ClassGroupResourceIT.createUpdatedEntity(em);
            em.persist(classGroup);
            em.flush();
        } else {
            classGroup = TestUtil.findAll(em, ClassGroup.class).get(0);
        }
        enrollment.setClassGroup(classGroup);
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Enrollment> enrollmentList = enrollmentRepository.findAll();
        assertThat(enrollmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
