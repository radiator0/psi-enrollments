package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.CourseUnit;
import com.psi.domain.Course;
import com.psi.repository.CourseUnitRepository;
import com.psi.service.CourseUnitService;
import com.psi.service.dto.CourseUnitDTO;
import com.psi.service.mapper.CourseUnitMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CourseUnitResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
@AutoConfigureMockMvc
@WithMockUser
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
    private EntityManager em;

    @Autowired
    private MockMvc restCourseUnitMockMvc;

    private CourseUnit courseUnit;

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
        // Add required entity
        Course course;
        if (TestUtil.findAll(em, Course.class).isEmpty()) {
            course = CourseResourceIT.createEntity(em);
            em.persist(course);
            em.flush();
        } else {
            course = TestUtil.findAll(em, Course.class).get(0);
        }
        courseUnit.getCourses().add(course);
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
        // Add required entity
        Course course;
        if (TestUtil.findAll(em, Course.class).isEmpty()) {
            course = CourseResourceIT.createUpdatedEntity(em);
            em.persist(course);
            em.flush();
        } else {
            course = TestUtil.findAll(em, Course.class).get(0);
        }
        courseUnit.getCourses().add(course);
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseUnit> courseUnitList = courseUnitRepository.findAll();
        assertThat(courseUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
