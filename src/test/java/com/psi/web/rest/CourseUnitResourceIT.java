package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.CourseUnit;
import com.psi.repository.CourseUnitRepository;
import com.psi.service.CourseUnitService;
import com.psi.service.dto.CourseUnitDTO;
import com.psi.service.mapper.CourseUnitMapper;
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

/**
 * Integration tests for the {@link CourseUnitResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
public class CourseUnitResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ECTS = 0;
    private static final Integer UPDATED_ECTS = 1;

    private static final Boolean DEFAULT_IS_GROUP_OF_COURSES = false;
    private static final Boolean UPDATED_IS_GROUP_OF_COURSES = true;

    private static final Boolean DEFAULT_IS_STREAM = false;
    private static final Boolean UPDATED_IS_STREAM = true;

    private static final Boolean DEFAULT_IS_SELECTABLE = false;
    private static final Boolean UPDATED_IS_SELECTABLE = true;

    @Autowired
    private CourseUnitRepository courseUnitRepository;

    @Autowired
    private CourseUnitMapper courseUnitMapper;

    @Autowired
    private CourseUnitService courseUnitService;

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

    private MockMvc restCourseUnitMockMvc;

    private CourseUnit courseUnit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CourseUnitResource courseUnitResource = new CourseUnitResource(courseUnitService);
        this.restCourseUnitMockMvc = MockMvcBuilders.standaloneSetup(courseUnitResource)
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
    public static CourseUnit createEntity(EntityManager em) {
        CourseUnit courseUnit = new CourseUnit()
            .code(DEFAULT_CODE)
            .ects(DEFAULT_ECTS)
            .isGroupOfCourses(DEFAULT_IS_GROUP_OF_COURSES)
            .isStream(DEFAULT_IS_STREAM)
            .isSelectable(DEFAULT_IS_SELECTABLE);
        return courseUnit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseUnit createUpdatedEntity(EntityManager em) {
        CourseUnit courseUnit = new CourseUnit()
            .code(UPDATED_CODE)
            .ects(UPDATED_ECTS)
            .isGroupOfCourses(UPDATED_IS_GROUP_OF_COURSES)
            .isStream(UPDATED_IS_STREAM)
            .isSelectable(UPDATED_IS_SELECTABLE);
        return courseUnit;
    }

    @BeforeEach
    public void initTest() {
        courseUnit = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourseUnit() throws Exception {
        int databaseSizeBeforeCreate = courseUnitRepository.findAll().size();

        // Create the CourseUnit
        CourseUnitDTO courseUnitDTO = courseUnitMapper.toDto(courseUnit);
        restCourseUnitMockMvc.perform(post("/api/course-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the CourseUnit in the database
        List<CourseUnit> courseUnitList = courseUnitRepository.findAll();
        assertThat(courseUnitList).hasSize(databaseSizeBeforeCreate + 1);
        CourseUnit testCourseUnit = courseUnitList.get(courseUnitList.size() - 1);
        assertThat(testCourseUnit.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCourseUnit.getEcts()).isEqualTo(DEFAULT_ECTS);
        assertThat(testCourseUnit.isIsGroupOfCourses()).isEqualTo(DEFAULT_IS_GROUP_OF_COURSES);
        assertThat(testCourseUnit.isIsStream()).isEqualTo(DEFAULT_IS_STREAM);
        assertThat(testCourseUnit.isIsSelectable()).isEqualTo(DEFAULT_IS_SELECTABLE);
    }

    @Test
    @Transactional
    public void createCourseUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseUnitRepository.findAll().size();

        // Create the CourseUnit with an existing ID
        courseUnit.setId(1L);
        CourseUnitDTO courseUnitDTO = courseUnitMapper.toDto(courseUnit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseUnitMockMvc.perform(post("/api/course-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourseUnit in the database
        List<CourseUnit> courseUnitList = courseUnitRepository.findAll();
        assertThat(courseUnitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIsGroupOfCoursesIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseUnitRepository.findAll().size();
        // set the field null
        courseUnit.setIsGroupOfCourses(null);

        // Create the CourseUnit, which fails.
        CourseUnitDTO courseUnitDTO = courseUnitMapper.toDto(courseUnit);

        restCourseUnitMockMvc.perform(post("/api/course-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseUnitDTO)))
            .andExpect(status().isBadRequest());

        List<CourseUnit> courseUnitList = courseUnitRepository.findAll();
        assertThat(courseUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsStreamIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseUnitRepository.findAll().size();
        // set the field null
        courseUnit.setIsStream(null);

        // Create the CourseUnit, which fails.
        CourseUnitDTO courseUnitDTO = courseUnitMapper.toDto(courseUnit);

        restCourseUnitMockMvc.perform(post("/api/course-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseUnitDTO)))
            .andExpect(status().isBadRequest());

        List<CourseUnit> courseUnitList = courseUnitRepository.findAll();
        assertThat(courseUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsSelectableIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseUnitRepository.findAll().size();
        // set the field null
        courseUnit.setIsSelectable(null);

        // Create the CourseUnit, which fails.
        CourseUnitDTO courseUnitDTO = courseUnitMapper.toDto(courseUnit);

        restCourseUnitMockMvc.perform(post("/api/course-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseUnitDTO)))
            .andExpect(status().isBadRequest());

        List<CourseUnit> courseUnitList = courseUnitRepository.findAll();
        assertThat(courseUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCourseUnits() throws Exception {
        // Initialize the database
        courseUnitRepository.saveAndFlush(courseUnit);

        // Get all the courseUnitList
        restCourseUnitMockMvc.perform(get("/api/course-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].ects").value(hasItem(DEFAULT_ECTS)))
            .andExpect(jsonPath("$.[*].isGroupOfCourses").value(hasItem(DEFAULT_IS_GROUP_OF_COURSES.booleanValue())))
            .andExpect(jsonPath("$.[*].isStream").value(hasItem(DEFAULT_IS_STREAM.booleanValue())))
            .andExpect(jsonPath("$.[*].isSelectable").value(hasItem(DEFAULT_IS_SELECTABLE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCourseUnit() throws Exception {
        // Initialize the database
        courseUnitRepository.saveAndFlush(courseUnit);

        // Get the courseUnit
        restCourseUnitMockMvc.perform(get("/api/course-units/{id}", courseUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courseUnit.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.ects").value(DEFAULT_ECTS))
            .andExpect(jsonPath("$.isGroupOfCourses").value(DEFAULT_IS_GROUP_OF_COURSES.booleanValue()))
            .andExpect(jsonPath("$.isStream").value(DEFAULT_IS_STREAM.booleanValue()))
            .andExpect(jsonPath("$.isSelectable").value(DEFAULT_IS_SELECTABLE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCourseUnit() throws Exception {
        // Get the courseUnit
        restCourseUnitMockMvc.perform(get("/api/course-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseUnit() throws Exception {
        // Initialize the database
        courseUnitRepository.saveAndFlush(courseUnit);

        int databaseSizeBeforeUpdate = courseUnitRepository.findAll().size();

        // Update the courseUnit
        CourseUnit updatedCourseUnit = courseUnitRepository.findById(courseUnit.getId()).get();
        // Disconnect from session so that the updates on updatedCourseUnit are not directly saved in db
        em.detach(updatedCourseUnit);
        updatedCourseUnit
            .code(UPDATED_CODE)
            .ects(UPDATED_ECTS)
            .isGroupOfCourses(UPDATED_IS_GROUP_OF_COURSES)
            .isStream(UPDATED_IS_STREAM)
            .isSelectable(UPDATED_IS_SELECTABLE);
        CourseUnitDTO courseUnitDTO = courseUnitMapper.toDto(updatedCourseUnit);

        restCourseUnitMockMvc.perform(put("/api/course-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseUnitDTO)))
            .andExpect(status().isOk());

        // Validate the CourseUnit in the database
        List<CourseUnit> courseUnitList = courseUnitRepository.findAll();
        assertThat(courseUnitList).hasSize(databaseSizeBeforeUpdate);
        CourseUnit testCourseUnit = courseUnitList.get(courseUnitList.size() - 1);
        assertThat(testCourseUnit.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCourseUnit.getEcts()).isEqualTo(UPDATED_ECTS);
        assertThat(testCourseUnit.isIsGroupOfCourses()).isEqualTo(UPDATED_IS_GROUP_OF_COURSES);
        assertThat(testCourseUnit.isIsStream()).isEqualTo(UPDATED_IS_STREAM);
        assertThat(testCourseUnit.isIsSelectable()).isEqualTo(UPDATED_IS_SELECTABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingCourseUnit() throws Exception {
        int databaseSizeBeforeUpdate = courseUnitRepository.findAll().size();

        // Create the CourseUnit
        CourseUnitDTO courseUnitDTO = courseUnitMapper.toDto(courseUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseUnitMockMvc.perform(put("/api/course-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CourseUnit in the database
        List<CourseUnit> courseUnitList = courseUnitRepository.findAll();
        assertThat(courseUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCourseUnit() throws Exception {
        // Initialize the database
        courseUnitRepository.saveAndFlush(courseUnit);

        int databaseSizeBeforeDelete = courseUnitRepository.findAll().size();

        // Delete the courseUnit
        restCourseUnitMockMvc.perform(delete("/api/course-units/{id}", courseUnit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseUnit> courseUnitList = courseUnitRepository.findAll();
        assertThat(courseUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseUnit.class);
        CourseUnit courseUnit1 = new CourseUnit();
        courseUnit1.setId(1L);
        CourseUnit courseUnit2 = new CourseUnit();
        courseUnit2.setId(courseUnit1.getId());
        assertThat(courseUnit1).isEqualTo(courseUnit2);
        courseUnit2.setId(2L);
        assertThat(courseUnit1).isNotEqualTo(courseUnit2);
        courseUnit1.setId(null);
        assertThat(courseUnit1).isNotEqualTo(courseUnit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseUnitDTO.class);
        CourseUnitDTO courseUnitDTO1 = new CourseUnitDTO();
        courseUnitDTO1.setId(1L);
        CourseUnitDTO courseUnitDTO2 = new CourseUnitDTO();
        assertThat(courseUnitDTO1).isNotEqualTo(courseUnitDTO2);
        courseUnitDTO2.setId(courseUnitDTO1.getId());
        assertThat(courseUnitDTO1).isEqualTo(courseUnitDTO2);
        courseUnitDTO2.setId(2L);
        assertThat(courseUnitDTO1).isNotEqualTo(courseUnitDTO2);
        courseUnitDTO1.setId(null);
        assertThat(courseUnitDTO1).isNotEqualTo(courseUnitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(courseUnitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(courseUnitMapper.fromId(null)).isNull();
    }
}
