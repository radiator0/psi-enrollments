package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.ClassGroup;
import com.psi.repository.ClassGroupRepository;
import com.psi.service.ClassGroupService;
import com.psi.service.dto.ClassGroupDTO;
import com.psi.service.mapper.ClassGroupMapper;
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
 * Integration tests for the {@link ClassGroupResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
public class ClassGroupResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ENROLLMENT_ABOVE_LIMIT_ALLOWED = false;
    private static final Boolean UPDATED_IS_ENROLLMENT_ABOVE_LIMIT_ALLOWED = true;

    private static final Integer DEFAULT_PEOPLE_LIMIT = 0;
    private static final Integer UPDATED_PEOPLE_LIMIT = 1;

    private static final Integer DEFAULT_ENROLLED_COUNT = 0;
    private static final Integer UPDATED_ENROLLED_COUNT = 1;

    private static final Boolean DEFAULT_IS_FULL = false;
    private static final Boolean UPDATED_IS_FULL = true;

    @Autowired
    private ClassGroupRepository classGroupRepository;

    @Autowired
    private ClassGroupMapper classGroupMapper;

    @Autowired
    private ClassGroupService classGroupService;

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

    private MockMvc restClassGroupMockMvc;

    private ClassGroup classGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassGroupResource classGroupResource = new ClassGroupResource(classGroupService);
        this.restClassGroupMockMvc = MockMvcBuilders.standaloneSetup(classGroupResource)
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
    public static ClassGroup createEntity(EntityManager em) {
        ClassGroup classGroup = new ClassGroup()
            .code(DEFAULT_CODE)
            .isEnrollmentAboveLimitAllowed(DEFAULT_IS_ENROLLMENT_ABOVE_LIMIT_ALLOWED)
            .peopleLimit(DEFAULT_PEOPLE_LIMIT)
            .enrolledCount(DEFAULT_ENROLLED_COUNT)
            .isFull(DEFAULT_IS_FULL);
        return classGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassGroup createUpdatedEntity(EntityManager em) {
        ClassGroup classGroup = new ClassGroup()
            .code(UPDATED_CODE)
            .isEnrollmentAboveLimitAllowed(UPDATED_IS_ENROLLMENT_ABOVE_LIMIT_ALLOWED)
            .peopleLimit(UPDATED_PEOPLE_LIMIT)
            .enrolledCount(UPDATED_ENROLLED_COUNT)
            .isFull(UPDATED_IS_FULL);
        return classGroup;
    }

    @BeforeEach
    public void initTest() {
        classGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassGroup() throws Exception {
        int databaseSizeBeforeCreate = classGroupRepository.findAll().size();

        // Create the ClassGroup
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(classGroup);
        restClassGroupMockMvc.perform(post("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassGroup in the database
        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ClassGroup testClassGroup = classGroupList.get(classGroupList.size() - 1);
        assertThat(testClassGroup.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testClassGroup.isIsEnrollmentAboveLimitAllowed()).isEqualTo(DEFAULT_IS_ENROLLMENT_ABOVE_LIMIT_ALLOWED);
        assertThat(testClassGroup.getPeopleLimit()).isEqualTo(DEFAULT_PEOPLE_LIMIT);
        assertThat(testClassGroup.getEnrolledCount()).isEqualTo(DEFAULT_ENROLLED_COUNT);
        assertThat(testClassGroup.isIsFull()).isEqualTo(DEFAULT_IS_FULL);
    }

    @Test
    @Transactional
    public void createClassGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classGroupRepository.findAll().size();

        // Create the ClassGroup with an existing ID
        classGroup.setId(1L);
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(classGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassGroupMockMvc.perform(post("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassGroup in the database
        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = classGroupRepository.findAll().size();
        // set the field null
        classGroup.setCode(null);

        // Create the ClassGroup, which fails.
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(classGroup);

        restClassGroupMockMvc.perform(post("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isBadRequest());

        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsEnrollmentAboveLimitAllowedIsRequired() throws Exception {
        int databaseSizeBeforeTest = classGroupRepository.findAll().size();
        // set the field null
        classGroup.setIsEnrollmentAboveLimitAllowed(null);

        // Create the ClassGroup, which fails.
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(classGroup);

        restClassGroupMockMvc.perform(post("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isBadRequest());

        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPeopleLimitIsRequired() throws Exception {
        int databaseSizeBeforeTest = classGroupRepository.findAll().size();
        // set the field null
        classGroup.setPeopleLimit(null);

        // Create the ClassGroup, which fails.
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(classGroup);

        restClassGroupMockMvc.perform(post("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isBadRequest());

        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnrolledCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = classGroupRepository.findAll().size();
        // set the field null
        classGroup.setEnrolledCount(null);

        // Create the ClassGroup, which fails.
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(classGroup);

        restClassGroupMockMvc.perform(post("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isBadRequest());

        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsFullIsRequired() throws Exception {
        int databaseSizeBeforeTest = classGroupRepository.findAll().size();
        // set the field null
        classGroup.setIsFull(null);

        // Create the ClassGroup, which fails.
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(classGroup);

        restClassGroupMockMvc.perform(post("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isBadRequest());

        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClassGroups() throws Exception {
        // Initialize the database
        classGroupRepository.saveAndFlush(classGroup);

        // Get all the classGroupList
        restClassGroupMockMvc.perform(get("/api/class-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].isEnrollmentAboveLimitAllowed").value(hasItem(DEFAULT_IS_ENROLLMENT_ABOVE_LIMIT_ALLOWED.booleanValue())))
            .andExpect(jsonPath("$.[*].peopleLimit").value(hasItem(DEFAULT_PEOPLE_LIMIT)))
            .andExpect(jsonPath("$.[*].enrolledCount").value(hasItem(DEFAULT_ENROLLED_COUNT)))
            .andExpect(jsonPath("$.[*].isFull").value(hasItem(DEFAULT_IS_FULL.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getClassGroup() throws Exception {
        // Initialize the database
        classGroupRepository.saveAndFlush(classGroup);

        // Get the classGroup
        restClassGroupMockMvc.perform(get("/api/class-groups/{id}", classGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classGroup.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.isEnrollmentAboveLimitAllowed").value(DEFAULT_IS_ENROLLMENT_ABOVE_LIMIT_ALLOWED.booleanValue()))
            .andExpect(jsonPath("$.peopleLimit").value(DEFAULT_PEOPLE_LIMIT))
            .andExpect(jsonPath("$.enrolledCount").value(DEFAULT_ENROLLED_COUNT))
            .andExpect(jsonPath("$.isFull").value(DEFAULT_IS_FULL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClassGroup() throws Exception {
        // Get the classGroup
        restClassGroupMockMvc.perform(get("/api/class-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassGroup() throws Exception {
        // Initialize the database
        classGroupRepository.saveAndFlush(classGroup);

        int databaseSizeBeforeUpdate = classGroupRepository.findAll().size();

        // Update the classGroup
        ClassGroup updatedClassGroup = classGroupRepository.findById(classGroup.getId()).get();
        // Disconnect from session so that the updates on updatedClassGroup are not directly saved in db
        em.detach(updatedClassGroup);
        updatedClassGroup
            .code(UPDATED_CODE)
            .isEnrollmentAboveLimitAllowed(UPDATED_IS_ENROLLMENT_ABOVE_LIMIT_ALLOWED)
            .peopleLimit(UPDATED_PEOPLE_LIMIT)
            .enrolledCount(UPDATED_ENROLLED_COUNT)
            .isFull(UPDATED_IS_FULL);
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(updatedClassGroup);

        restClassGroupMockMvc.perform(put("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isOk());

        // Validate the ClassGroup in the database
        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeUpdate);
        ClassGroup testClassGroup = classGroupList.get(classGroupList.size() - 1);
        assertThat(testClassGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testClassGroup.isIsEnrollmentAboveLimitAllowed()).isEqualTo(UPDATED_IS_ENROLLMENT_ABOVE_LIMIT_ALLOWED);
        assertThat(testClassGroup.getPeopleLimit()).isEqualTo(UPDATED_PEOPLE_LIMIT);
        assertThat(testClassGroup.getEnrolledCount()).isEqualTo(UPDATED_ENROLLED_COUNT);
        assertThat(testClassGroup.isIsFull()).isEqualTo(UPDATED_IS_FULL);
    }

    @Test
    @Transactional
    public void updateNonExistingClassGroup() throws Exception {
        int databaseSizeBeforeUpdate = classGroupRepository.findAll().size();

        // Create the ClassGroup
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(classGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassGroupMockMvc.perform(put("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassGroup in the database
        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClassGroup() throws Exception {
        // Initialize the database
        classGroupRepository.saveAndFlush(classGroup);

        int databaseSizeBeforeDelete = classGroupRepository.findAll().size();

        // Delete the classGroup
        restClassGroupMockMvc.perform(delete("/api/class-groups/{id}", classGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassGroup.class);
        ClassGroup classGroup1 = new ClassGroup();
        classGroup1.setId(1L);
        ClassGroup classGroup2 = new ClassGroup();
        classGroup2.setId(classGroup1.getId());
        assertThat(classGroup1).isEqualTo(classGroup2);
        classGroup2.setId(2L);
        assertThat(classGroup1).isNotEqualTo(classGroup2);
        classGroup1.setId(null);
        assertThat(classGroup1).isNotEqualTo(classGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassGroupDTO.class);
        ClassGroupDTO classGroupDTO1 = new ClassGroupDTO();
        classGroupDTO1.setId(1L);
        ClassGroupDTO classGroupDTO2 = new ClassGroupDTO();
        assertThat(classGroupDTO1).isNotEqualTo(classGroupDTO2);
        classGroupDTO2.setId(classGroupDTO1.getId());
        assertThat(classGroupDTO1).isEqualTo(classGroupDTO2);
        classGroupDTO2.setId(2L);
        assertThat(classGroupDTO1).isNotEqualTo(classGroupDTO2);
        classGroupDTO1.setId(null);
        assertThat(classGroupDTO1).isNotEqualTo(classGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(classGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(classGroupMapper.fromId(null)).isNull();
    }
}
