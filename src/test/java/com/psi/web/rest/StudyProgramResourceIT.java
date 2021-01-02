package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.StudyProgram;
import com.psi.repository.StudyProgramRepository;
import com.psi.service.StudyProgramService;
import com.psi.service.dto.StudyProgramDTO;
import com.psi.service.mapper.StudyProgramMapper;
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
import java.util.List;

import static com.psi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.psi.domain.enumeration.SemesterType;
import com.psi.domain.enumeration.StudyType;
import com.psi.domain.enumeration.StudyForm;
/**
 * Integration tests for the {@link StudyProgramResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
public class StudyProgramResourceIT {

    private static final Integer DEFAULT_START_YEAR = 0;
    private static final Integer UPDATED_START_YEAR = 1;

    private static final Integer DEFAULT_END_YEAR = 0;
    private static final Integer UPDATED_END_YEAR = 1;

    private static final SemesterType DEFAULT_STARTING_SEMESTER_TYPE = SemesterType.Winter;
    private static final SemesterType UPDATED_STARTING_SEMESTER_TYPE = SemesterType.Summer;

    private static final StudyType DEFAULT_STUDY_TYPE = StudyType.Engineering;
    private static final StudyType UPDATED_STUDY_TYPE = StudyType.Masters;

    private static final StudyForm DEFAULT_STUDY_FORM = StudyForm.Stationary;
    private static final StudyForm UPDATED_STUDY_FORM = StudyForm.NonStationary;

    @Autowired
    private StudyProgramRepository studyProgramRepository;

    @Autowired
    private StudyProgramMapper studyProgramMapper;

    @Autowired
    private StudyProgramService studyProgramService;

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

    private MockMvc restStudyProgramMockMvc;

    private StudyProgram studyProgram;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudyProgramResource studyProgramResource = new StudyProgramResource(studyProgramService);
        this.restStudyProgramMockMvc = MockMvcBuilders.standaloneSetup(studyProgramResource)
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
    public static StudyProgram createEntity(EntityManager em) {
        StudyProgram studyProgram = new StudyProgram()
            .startYear(DEFAULT_START_YEAR)
            .endYear(DEFAULT_END_YEAR)
            .startingSemesterType(DEFAULT_STARTING_SEMESTER_TYPE)
            .studyType(DEFAULT_STUDY_TYPE)
            .studyForm(DEFAULT_STUDY_FORM);
        return studyProgram;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudyProgram createUpdatedEntity(EntityManager em) {
        StudyProgram studyProgram = new StudyProgram()
            .startYear(UPDATED_START_YEAR)
            .endYear(UPDATED_END_YEAR)
            .startingSemesterType(UPDATED_STARTING_SEMESTER_TYPE)
            .studyType(UPDATED_STUDY_TYPE)
            .studyForm(UPDATED_STUDY_FORM);
        return studyProgram;
    }

    @BeforeEach
    public void initTest() {
        studyProgram = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudyProgram() throws Exception {
        int databaseSizeBeforeCreate = studyProgramRepository.findAll().size();

        // Create the StudyProgram
        StudyProgramDTO studyProgramDTO = studyProgramMapper.toDto(studyProgram);
        restStudyProgramMockMvc.perform(post("/api/study-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studyProgramDTO)))
            .andExpect(status().isCreated());

        // Validate the StudyProgram in the database
        List<StudyProgram> studyProgramList = studyProgramRepository.findAll();
        assertThat(studyProgramList).hasSize(databaseSizeBeforeCreate + 1);
        StudyProgram testStudyProgram = studyProgramList.get(studyProgramList.size() - 1);
        assertThat(testStudyProgram.getStartYear()).isEqualTo(DEFAULT_START_YEAR);
        assertThat(testStudyProgram.getEndYear()).isEqualTo(DEFAULT_END_YEAR);
        assertThat(testStudyProgram.getStartingSemesterType()).isEqualTo(DEFAULT_STARTING_SEMESTER_TYPE);
        assertThat(testStudyProgram.getStudyType()).isEqualTo(DEFAULT_STUDY_TYPE);
        assertThat(testStudyProgram.getStudyForm()).isEqualTo(DEFAULT_STUDY_FORM);
    }

    @Test
    @Transactional
    public void createStudyProgramWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studyProgramRepository.findAll().size();

        // Create the StudyProgram with an existing ID
        studyProgram.setId(1L);
        StudyProgramDTO studyProgramDTO = studyProgramMapper.toDto(studyProgram);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudyProgramMockMvc.perform(post("/api/study-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studyProgramDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudyProgram in the database
        List<StudyProgram> studyProgramList = studyProgramRepository.findAll();
        assertThat(studyProgramList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStartYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = studyProgramRepository.findAll().size();
        // set the field null
        studyProgram.setStartYear(null);

        // Create the StudyProgram, which fails.
        StudyProgramDTO studyProgramDTO = studyProgramMapper.toDto(studyProgram);

        restStudyProgramMockMvc.perform(post("/api/study-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studyProgramDTO)))
            .andExpect(status().isBadRequest());

        List<StudyProgram> studyProgramList = studyProgramRepository.findAll();
        assertThat(studyProgramList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = studyProgramRepository.findAll().size();
        // set the field null
        studyProgram.setEndYear(null);

        // Create the StudyProgram, which fails.
        StudyProgramDTO studyProgramDTO = studyProgramMapper.toDto(studyProgram);

        restStudyProgramMockMvc.perform(post("/api/study-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studyProgramDTO)))
            .andExpect(status().isBadRequest());

        List<StudyProgram> studyProgramList = studyProgramRepository.findAll();
        assertThat(studyProgramList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartingSemesterTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = studyProgramRepository.findAll().size();
        // set the field null
        studyProgram.setStartingSemesterType(null);

        // Create the StudyProgram, which fails.
        StudyProgramDTO studyProgramDTO = studyProgramMapper.toDto(studyProgram);

        restStudyProgramMockMvc.perform(post("/api/study-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studyProgramDTO)))
            .andExpect(status().isBadRequest());

        List<StudyProgram> studyProgramList = studyProgramRepository.findAll();
        assertThat(studyProgramList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStudyTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = studyProgramRepository.findAll().size();
        // set the field null
        studyProgram.setStudyType(null);

        // Create the StudyProgram, which fails.
        StudyProgramDTO studyProgramDTO = studyProgramMapper.toDto(studyProgram);

        restStudyProgramMockMvc.perform(post("/api/study-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studyProgramDTO)))
            .andExpect(status().isBadRequest());

        List<StudyProgram> studyProgramList = studyProgramRepository.findAll();
        assertThat(studyProgramList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStudyFormIsRequired() throws Exception {
        int databaseSizeBeforeTest = studyProgramRepository.findAll().size();
        // set the field null
        studyProgram.setStudyForm(null);

        // Create the StudyProgram, which fails.
        StudyProgramDTO studyProgramDTO = studyProgramMapper.toDto(studyProgram);

        restStudyProgramMockMvc.perform(post("/api/study-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studyProgramDTO)))
            .andExpect(status().isBadRequest());

        List<StudyProgram> studyProgramList = studyProgramRepository.findAll();
        assertThat(studyProgramList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudyPrograms() throws Exception {
        // Initialize the database
        studyProgramRepository.saveAndFlush(studyProgram);

        // Get all the studyProgramList
        restStudyProgramMockMvc.perform(get("/api/study-programs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studyProgram.getId().intValue())))
            .andExpect(jsonPath("$.[*].startYear").value(hasItem(DEFAULT_START_YEAR)))
            .andExpect(jsonPath("$.[*].endYear").value(hasItem(DEFAULT_END_YEAR)))
            .andExpect(jsonPath("$.[*].startingSemesterType").value(hasItem(DEFAULT_STARTING_SEMESTER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].studyType").value(hasItem(DEFAULT_STUDY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].studyForm").value(hasItem(DEFAULT_STUDY_FORM.toString())));
    }
    
    @Test
    @Transactional
    public void getStudyProgram() throws Exception {
        // Initialize the database
        studyProgramRepository.saveAndFlush(studyProgram);

        // Get the studyProgram
        restStudyProgramMockMvc.perform(get("/api/study-programs/{id}", studyProgram.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studyProgram.getId().intValue()))
            .andExpect(jsonPath("$.startYear").value(DEFAULT_START_YEAR))
            .andExpect(jsonPath("$.endYear").value(DEFAULT_END_YEAR))
            .andExpect(jsonPath("$.startingSemesterType").value(DEFAULT_STARTING_SEMESTER_TYPE.toString()))
            .andExpect(jsonPath("$.studyType").value(DEFAULT_STUDY_TYPE.toString()))
            .andExpect(jsonPath("$.studyForm").value(DEFAULT_STUDY_FORM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudyProgram() throws Exception {
        // Get the studyProgram
        restStudyProgramMockMvc.perform(get("/api/study-programs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudyProgram() throws Exception {
        // Initialize the database
        studyProgramRepository.saveAndFlush(studyProgram);

        int databaseSizeBeforeUpdate = studyProgramRepository.findAll().size();

        // Update the studyProgram
        StudyProgram updatedStudyProgram = studyProgramRepository.findById(studyProgram.getId()).get();
        // Disconnect from session so that the updates on updatedStudyProgram are not directly saved in db
        em.detach(updatedStudyProgram);
        updatedStudyProgram
            .startYear(UPDATED_START_YEAR)
            .endYear(UPDATED_END_YEAR)
            .startingSemesterType(UPDATED_STARTING_SEMESTER_TYPE)
            .studyType(UPDATED_STUDY_TYPE)
            .studyForm(UPDATED_STUDY_FORM);
        StudyProgramDTO studyProgramDTO = studyProgramMapper.toDto(updatedStudyProgram);

        restStudyProgramMockMvc.perform(put("/api/study-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studyProgramDTO)))
            .andExpect(status().isOk());

        // Validate the StudyProgram in the database
        List<StudyProgram> studyProgramList = studyProgramRepository.findAll();
        assertThat(studyProgramList).hasSize(databaseSizeBeforeUpdate);
        StudyProgram testStudyProgram = studyProgramList.get(studyProgramList.size() - 1);
        assertThat(testStudyProgram.getStartYear()).isEqualTo(UPDATED_START_YEAR);
        assertThat(testStudyProgram.getEndYear()).isEqualTo(UPDATED_END_YEAR);
        assertThat(testStudyProgram.getStartingSemesterType()).isEqualTo(UPDATED_STARTING_SEMESTER_TYPE);
        assertThat(testStudyProgram.getStudyType()).isEqualTo(UPDATED_STUDY_TYPE);
        assertThat(testStudyProgram.getStudyForm()).isEqualTo(UPDATED_STUDY_FORM);
    }

    @Test
    @Transactional
    public void updateNonExistingStudyProgram() throws Exception {
        int databaseSizeBeforeUpdate = studyProgramRepository.findAll().size();

        // Create the StudyProgram
        StudyProgramDTO studyProgramDTO = studyProgramMapper.toDto(studyProgram);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudyProgramMockMvc.perform(put("/api/study-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studyProgramDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudyProgram in the database
        List<StudyProgram> studyProgramList = studyProgramRepository.findAll();
        assertThat(studyProgramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudyProgram() throws Exception {
        // Initialize the database
        studyProgramRepository.saveAndFlush(studyProgram);

        int databaseSizeBeforeDelete = studyProgramRepository.findAll().size();

        // Delete the studyProgram
        restStudyProgramMockMvc.perform(delete("/api/study-programs/{id}", studyProgram.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudyProgram> studyProgramList = studyProgramRepository.findAll();
        assertThat(studyProgramList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudyProgram.class);
        StudyProgram studyProgram1 = new StudyProgram();
        studyProgram1.setId(1L);
        StudyProgram studyProgram2 = new StudyProgram();
        studyProgram2.setId(studyProgram1.getId());
        assertThat(studyProgram1).isEqualTo(studyProgram2);
        studyProgram2.setId(2L);
        assertThat(studyProgram1).isNotEqualTo(studyProgram2);
        studyProgram1.setId(null);
        assertThat(studyProgram1).isNotEqualTo(studyProgram2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudyProgramDTO.class);
        StudyProgramDTO studyProgramDTO1 = new StudyProgramDTO();
        studyProgramDTO1.setId(1L);
        StudyProgramDTO studyProgramDTO2 = new StudyProgramDTO();
        assertThat(studyProgramDTO1).isNotEqualTo(studyProgramDTO2);
        studyProgramDTO2.setId(studyProgramDTO1.getId());
        assertThat(studyProgramDTO1).isEqualTo(studyProgramDTO2);
        studyProgramDTO2.setId(2L);
        assertThat(studyProgramDTO1).isNotEqualTo(studyProgramDTO2);
        studyProgramDTO1.setId(null);
        assertThat(studyProgramDTO1).isNotEqualTo(studyProgramDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studyProgramMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studyProgramMapper.fromId(null)).isNull();
    }
}
