package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.EnrollmentRight;
import com.psi.domain.EnrollmentDate;
import com.psi.domain.Student;
import com.psi.repository.EnrollmentRightRepository;
import com.psi.service.EnrollmentRightService;
import com.psi.service.dto.EnrollmentRightDTO;
import com.psi.service.mapper.EnrollmentRightMapper;

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
 * Integration tests for the {@link EnrollmentRightResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EnrollmentRightResourceIT {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EnrollmentRightRepository enrollmentRightRepository;

    @Autowired
    private EnrollmentRightMapper enrollmentRightMapper;

    @Autowired
    private EnrollmentRightService enrollmentRightService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnrollmentRightMockMvc;

    private EnrollmentRight enrollmentRight;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnrollmentRight createEntity(EntityManager em) {
        EnrollmentRight enrollmentRight = new EnrollmentRight()
            .startDate(DEFAULT_START_DATE);
        // Add required entity
        EnrollmentDate enrollmentDate;
        if (TestUtil.findAll(em, EnrollmentDate.class).isEmpty()) {
            enrollmentDate = EnrollmentDateResourceIT.createEntity(em);
            em.persist(enrollmentDate);
            em.flush();
        } else {
            enrollmentDate = TestUtil.findAll(em, EnrollmentDate.class).get(0);
        }
        enrollmentRight.setEnrollmentDate(enrollmentDate);
        // Add required entity
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            student = StudentResourceIT.createEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        enrollmentRight.setStudent(student);
        return enrollmentRight;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnrollmentRight createUpdatedEntity(EntityManager em) {
        EnrollmentRight enrollmentRight = new EnrollmentRight()
            .startDate(UPDATED_START_DATE);
        // Add required entity
        EnrollmentDate enrollmentDate;
        if (TestUtil.findAll(em, EnrollmentDate.class).isEmpty()) {
            enrollmentDate = EnrollmentDateResourceIT.createUpdatedEntity(em);
            em.persist(enrollmentDate);
            em.flush();
        } else {
            enrollmentDate = TestUtil.findAll(em, EnrollmentDate.class).get(0);
        }
        enrollmentRight.setEnrollmentDate(enrollmentDate);
        // Add required entity
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            student = StudentResourceIT.createUpdatedEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        enrollmentRight.setStudent(student);
        return enrollmentRight;
    }

    @BeforeEach
    public void initTest() {
        enrollmentRight = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnrollmentRight() throws Exception {
        int databaseSizeBeforeCreate = enrollmentRightRepository.findAll().size();
        // Create the EnrollmentRight
        EnrollmentRightDTO enrollmentRightDTO = enrollmentRightMapper.toDto(enrollmentRight);
        restEnrollmentRightMockMvc.perform(post("/api/enrollment-rights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentRightDTO)))
            .andExpect(status().isCreated());

        // Validate the EnrollmentRight in the database
        List<EnrollmentRight> enrollmentRightList = enrollmentRightRepository.findAll();
        assertThat(enrollmentRightList).hasSize(databaseSizeBeforeCreate + 1);
        EnrollmentRight testEnrollmentRight = enrollmentRightList.get(enrollmentRightList.size() - 1);
        assertThat(testEnrollmentRight.getStartDate()).isEqualTo(DEFAULT_START_DATE);
    }

    @Test
    @Transactional
    public void createEnrollmentRightWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enrollmentRightRepository.findAll().size();

        // Create the EnrollmentRight with an existing ID
        enrollmentRight.setId(1L);
        EnrollmentRightDTO enrollmentRightDTO = enrollmentRightMapper.toDto(enrollmentRight);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnrollmentRightMockMvc.perform(post("/api/enrollment-rights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentRightDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnrollmentRight in the database
        List<EnrollmentRight> enrollmentRightList = enrollmentRightRepository.findAll();
        assertThat(enrollmentRightList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrollmentRightRepository.findAll().size();
        // set the field null
        enrollmentRight.setStartDate(null);

        // Create the EnrollmentRight, which fails.
        EnrollmentRightDTO enrollmentRightDTO = enrollmentRightMapper.toDto(enrollmentRight);


        restEnrollmentRightMockMvc.perform(post("/api/enrollment-rights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentRightDTO)))
            .andExpect(status().isBadRequest());

        List<EnrollmentRight> enrollmentRightList = enrollmentRightRepository.findAll();
        assertThat(enrollmentRightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnrollmentRights() throws Exception {
        // Initialize the database
        enrollmentRightRepository.saveAndFlush(enrollmentRight);

        // Get all the enrollmentRightList
        restEnrollmentRightMockMvc.perform(get("/api/enrollment-rights?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enrollmentRight.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEnrollmentRight() throws Exception {
        // Initialize the database
        enrollmentRightRepository.saveAndFlush(enrollmentRight);

        // Get the enrollmentRight
        restEnrollmentRightMockMvc.perform(get("/api/enrollment-rights/{id}", enrollmentRight.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enrollmentRight.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEnrollmentRight() throws Exception {
        // Get the enrollmentRight
        restEnrollmentRightMockMvc.perform(get("/api/enrollment-rights/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnrollmentRight() throws Exception {
        // Initialize the database
        enrollmentRightRepository.saveAndFlush(enrollmentRight);

        int databaseSizeBeforeUpdate = enrollmentRightRepository.findAll().size();

        // Update the enrollmentRight
        EnrollmentRight updatedEnrollmentRight = enrollmentRightRepository.findById(enrollmentRight.getId()).get();
        // Disconnect from session so that the updates on updatedEnrollmentRight are not directly saved in db
        em.detach(updatedEnrollmentRight);
        updatedEnrollmentRight
            .startDate(UPDATED_START_DATE);
        EnrollmentRightDTO enrollmentRightDTO = enrollmentRightMapper.toDto(updatedEnrollmentRight);

        restEnrollmentRightMockMvc.perform(put("/api/enrollment-rights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentRightDTO)))
            .andExpect(status().isOk());

        // Validate the EnrollmentRight in the database
        List<EnrollmentRight> enrollmentRightList = enrollmentRightRepository.findAll();
        assertThat(enrollmentRightList).hasSize(databaseSizeBeforeUpdate);
        EnrollmentRight testEnrollmentRight = enrollmentRightList.get(enrollmentRightList.size() - 1);
        assertThat(testEnrollmentRight.getStartDate()).isEqualTo(UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEnrollmentRight() throws Exception {
        int databaseSizeBeforeUpdate = enrollmentRightRepository.findAll().size();

        // Create the EnrollmentRight
        EnrollmentRightDTO enrollmentRightDTO = enrollmentRightMapper.toDto(enrollmentRight);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnrollmentRightMockMvc.perform(put("/api/enrollment-rights")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentRightDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnrollmentRight in the database
        List<EnrollmentRight> enrollmentRightList = enrollmentRightRepository.findAll();
        assertThat(enrollmentRightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnrollmentRight() throws Exception {
        // Initialize the database
        enrollmentRightRepository.saveAndFlush(enrollmentRight);

        int databaseSizeBeforeDelete = enrollmentRightRepository.findAll().size();

        // Delete the enrollmentRight
        restEnrollmentRightMockMvc.perform(delete("/api/enrollment-rights/{id}", enrollmentRight.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EnrollmentRight> enrollmentRightList = enrollmentRightRepository.findAll();
        assertThat(enrollmentRightList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
