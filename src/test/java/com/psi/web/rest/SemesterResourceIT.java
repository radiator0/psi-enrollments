package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.Semester;
import com.psi.domain.FieldOfStudy;
import com.psi.repository.SemesterRepository;
import com.psi.service.SemesterService;
import com.psi.service.dto.SemesterDTO;
import com.psi.service.mapper.SemesterMapper;

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

import com.psi.domain.enumeration.SemesterType;
/**
 * Integration tests for the {@link SemesterResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SemesterResourceIT {

    private static final Integer DEFAULT_NUMBER = 0;
    private static final Integer UPDATED_NUMBER = 1;

    private static final SemesterType DEFAULT_SEMESTER_TYPE = SemesterType.Winter;
    private static final SemesterType UPDATED_SEMESTER_TYPE = SemesterType.Summer;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private SemesterMapper semesterMapper;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSemesterMockMvc;

    private Semester semester;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Semester createEntity(EntityManager em) {
        Semester semester = new Semester()
            .number(DEFAULT_NUMBER)
            .semesterType(DEFAULT_SEMESTER_TYPE)
            .startDate(DEFAULT_START_DATE);
        // Add required entity
        FieldOfStudy fieldOfStudy;
        if (TestUtil.findAll(em, FieldOfStudy.class).isEmpty()) {
            fieldOfStudy = FieldOfStudyResourceIT.createEntity(em);
            em.persist(fieldOfStudy);
            em.flush();
        } else {
            fieldOfStudy = TestUtil.findAll(em, FieldOfStudy.class).get(0);
        }
        semester.setFieldOfStudy(fieldOfStudy);
        return semester;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Semester createUpdatedEntity(EntityManager em) {
        Semester semester = new Semester()
            .number(UPDATED_NUMBER)
            .semesterType(UPDATED_SEMESTER_TYPE)
            .startDate(UPDATED_START_DATE);
        // Add required entity
        FieldOfStudy fieldOfStudy;
        if (TestUtil.findAll(em, FieldOfStudy.class).isEmpty()) {
            fieldOfStudy = FieldOfStudyResourceIT.createUpdatedEntity(em);
            em.persist(fieldOfStudy);
            em.flush();
        } else {
            fieldOfStudy = TestUtil.findAll(em, FieldOfStudy.class).get(0);
        }
        semester.setFieldOfStudy(fieldOfStudy);
        return semester;
    }

    @BeforeEach
    public void initTest() {
        semester = createEntity(em);
    }

    @Test
    @Transactional
    public void createSemester() throws Exception {
        int databaseSizeBeforeCreate = semesterRepository.findAll().size();
        // Create the Semester
        SemesterDTO semesterDTO = semesterMapper.toDto(semester);
        restSemesterMockMvc.perform(post("/api/semesters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semesterDTO)))
            .andExpect(status().isCreated());

        // Validate the Semester in the database
        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeCreate + 1);
        Semester testSemester = semesterList.get(semesterList.size() - 1);
        assertThat(testSemester.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testSemester.getSemesterType()).isEqualTo(DEFAULT_SEMESTER_TYPE);
        assertThat(testSemester.getStartDate()).isEqualTo(DEFAULT_START_DATE);
    }

    @Test
    @Transactional
    public void createSemesterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = semesterRepository.findAll().size();

        // Create the Semester with an existing ID
        semester.setId(1L);
        SemesterDTO semesterDTO = semesterMapper.toDto(semester);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSemesterMockMvc.perform(post("/api/semesters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semesterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Semester in the database
        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = semesterRepository.findAll().size();
        // set the field null
        semester.setNumber(null);

        // Create the Semester, which fails.
        SemesterDTO semesterDTO = semesterMapper.toDto(semester);


        restSemesterMockMvc.perform(post("/api/semesters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semesterDTO)))
            .andExpect(status().isBadRequest());

        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSemesterTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = semesterRepository.findAll().size();
        // set the field null
        semester.setSemesterType(null);

        // Create the Semester, which fails.
        SemesterDTO semesterDTO = semesterMapper.toDto(semester);


        restSemesterMockMvc.perform(post("/api/semesters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semesterDTO)))
            .andExpect(status().isBadRequest());

        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = semesterRepository.findAll().size();
        // set the field null
        semester.setStartDate(null);

        // Create the Semester, which fails.
        SemesterDTO semesterDTO = semesterMapper.toDto(semester);


        restSemesterMockMvc.perform(post("/api/semesters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semesterDTO)))
            .andExpect(status().isBadRequest());

        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSemesters() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get all the semesterList
        restSemesterMockMvc.perform(get("/api/semesters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(semester.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].semesterType").value(hasItem(DEFAULT_SEMESTER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getSemester() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        // Get the semester
        restSemesterMockMvc.perform(get("/api/semesters/{id}", semester.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(semester.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.semesterType").value(DEFAULT_SEMESTER_TYPE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSemester() throws Exception {
        // Get the semester
        restSemesterMockMvc.perform(get("/api/semesters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSemester() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        int databaseSizeBeforeUpdate = semesterRepository.findAll().size();

        // Update the semester
        Semester updatedSemester = semesterRepository.findById(semester.getId()).get();
        // Disconnect from session so that the updates on updatedSemester are not directly saved in db
        em.detach(updatedSemester);
        updatedSemester
            .number(UPDATED_NUMBER)
            .semesterType(UPDATED_SEMESTER_TYPE)
            .startDate(UPDATED_START_DATE);
        SemesterDTO semesterDTO = semesterMapper.toDto(updatedSemester);

        restSemesterMockMvc.perform(put("/api/semesters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semesterDTO)))
            .andExpect(status().isOk());

        // Validate the Semester in the database
        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeUpdate);
        Semester testSemester = semesterList.get(semesterList.size() - 1);
        assertThat(testSemester.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testSemester.getSemesterType()).isEqualTo(UPDATED_SEMESTER_TYPE);
        assertThat(testSemester.getStartDate()).isEqualTo(UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSemester() throws Exception {
        int databaseSizeBeforeUpdate = semesterRepository.findAll().size();

        // Create the Semester
        SemesterDTO semesterDTO = semesterMapper.toDto(semester);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSemesterMockMvc.perform(put("/api/semesters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semesterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Semester in the database
        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSemester() throws Exception {
        // Initialize the database
        semesterRepository.saveAndFlush(semester);

        int databaseSizeBeforeDelete = semesterRepository.findAll().size();

        // Delete the semester
        restSemesterMockMvc.perform(delete("/api/semesters/{id}", semester.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
