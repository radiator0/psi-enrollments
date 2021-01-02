package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.Semester;
import com.psi.repository.SemesterRepository;
import com.psi.service.SemesterService;
import com.psi.service.dto.SemesterDTO;
import com.psi.service.mapper.SemesterMapper;
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

import com.psi.domain.enumeration.SemesterType;
/**
 * Integration tests for the {@link SemesterResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
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
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSemesterMockMvc;

    private Semester semester;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SemesterResource semesterResource = new SemesterResource(semesterService);
        this.restSemesterMockMvc = MockMvcBuilders.standaloneSetup(semesterResource)
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
    public static Semester createEntity(EntityManager em) {
        Semester semester = new Semester()
            .number(DEFAULT_NUMBER)
            .semesterType(DEFAULT_SEMESTER_TYPE)
            .startDate(DEFAULT_START_DATE);
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Semester> semesterList = semesterRepository.findAll();
        assertThat(semesterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Semester.class);
        Semester semester1 = new Semester();
        semester1.setId(1L);
        Semester semester2 = new Semester();
        semester2.setId(semester1.getId());
        assertThat(semester1).isEqualTo(semester2);
        semester2.setId(2L);
        assertThat(semester1).isNotEqualTo(semester2);
        semester1.setId(null);
        assertThat(semester1).isNotEqualTo(semester2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SemesterDTO.class);
        SemesterDTO semesterDTO1 = new SemesterDTO();
        semesterDTO1.setId(1L);
        SemesterDTO semesterDTO2 = new SemesterDTO();
        assertThat(semesterDTO1).isNotEqualTo(semesterDTO2);
        semesterDTO2.setId(semesterDTO1.getId());
        assertThat(semesterDTO1).isEqualTo(semesterDTO2);
        semesterDTO2.setId(2L);
        assertThat(semesterDTO1).isNotEqualTo(semesterDTO2);
        semesterDTO1.setId(null);
        assertThat(semesterDTO1).isNotEqualTo(semesterDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(semesterMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(semesterMapper.fromId(null)).isNull();
    }
}
