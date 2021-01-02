package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.EnrollmentUnit;
import com.psi.repository.EnrollmentUnitRepository;
import com.psi.service.EnrollmentUnitService;
import com.psi.service.dto.EnrollmentUnitDTO;
import com.psi.service.mapper.EnrollmentUnitMapper;
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
 * Integration tests for the {@link EnrollmentUnitResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
public class EnrollmentUnitResourceIT {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EnrollmentUnitRepository enrollmentUnitRepository;

    @Autowired
    private EnrollmentUnitMapper enrollmentUnitMapper;

    @Autowired
    private EnrollmentUnitService enrollmentUnitService;

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

    private MockMvc restEnrollmentUnitMockMvc;

    private EnrollmentUnit enrollmentUnit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnrollmentUnitResource enrollmentUnitResource = new EnrollmentUnitResource(enrollmentUnitService);
        this.restEnrollmentUnitMockMvc = MockMvcBuilders.standaloneSetup(enrollmentUnitResource)
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
    public static EnrollmentUnit createEntity(EntityManager em) {
        EnrollmentUnit enrollmentUnit = new EnrollmentUnit()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return enrollmentUnit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnrollmentUnit createUpdatedEntity(EntityManager em) {
        EnrollmentUnit enrollmentUnit = new EnrollmentUnit()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return enrollmentUnit;
    }

    @BeforeEach
    public void initTest() {
        enrollmentUnit = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnrollmentUnit() throws Exception {
        int databaseSizeBeforeCreate = enrollmentUnitRepository.findAll().size();

        // Create the EnrollmentUnit
        EnrollmentUnitDTO enrollmentUnitDTO = enrollmentUnitMapper.toDto(enrollmentUnit);
        restEnrollmentUnitMockMvc.perform(post("/api/enrollment-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the EnrollmentUnit in the database
        List<EnrollmentUnit> enrollmentUnitList = enrollmentUnitRepository.findAll();
        assertThat(enrollmentUnitList).hasSize(databaseSizeBeforeCreate + 1);
        EnrollmentUnit testEnrollmentUnit = enrollmentUnitList.get(enrollmentUnitList.size() - 1);
        assertThat(testEnrollmentUnit.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEnrollmentUnit.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createEnrollmentUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enrollmentUnitRepository.findAll().size();

        // Create the EnrollmentUnit with an existing ID
        enrollmentUnit.setId(1L);
        EnrollmentUnitDTO enrollmentUnitDTO = enrollmentUnitMapper.toDto(enrollmentUnit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnrollmentUnitMockMvc.perform(post("/api/enrollment-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnrollmentUnit in the database
        List<EnrollmentUnit> enrollmentUnitList = enrollmentUnitRepository.findAll();
        assertThat(enrollmentUnitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrollmentUnitRepository.findAll().size();
        // set the field null
        enrollmentUnit.setStartDate(null);

        // Create the EnrollmentUnit, which fails.
        EnrollmentUnitDTO enrollmentUnitDTO = enrollmentUnitMapper.toDto(enrollmentUnit);

        restEnrollmentUnitMockMvc.perform(post("/api/enrollment-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentUnitDTO)))
            .andExpect(status().isBadRequest());

        List<EnrollmentUnit> enrollmentUnitList = enrollmentUnitRepository.findAll();
        assertThat(enrollmentUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = enrollmentUnitRepository.findAll().size();
        // set the field null
        enrollmentUnit.setEndDate(null);

        // Create the EnrollmentUnit, which fails.
        EnrollmentUnitDTO enrollmentUnitDTO = enrollmentUnitMapper.toDto(enrollmentUnit);

        restEnrollmentUnitMockMvc.perform(post("/api/enrollment-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentUnitDTO)))
            .andExpect(status().isBadRequest());

        List<EnrollmentUnit> enrollmentUnitList = enrollmentUnitRepository.findAll();
        assertThat(enrollmentUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnrollmentUnits() throws Exception {
        // Initialize the database
        enrollmentUnitRepository.saveAndFlush(enrollmentUnit);

        // Get all the enrollmentUnitList
        restEnrollmentUnitMockMvc.perform(get("/api/enrollment-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enrollmentUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEnrollmentUnit() throws Exception {
        // Initialize the database
        enrollmentUnitRepository.saveAndFlush(enrollmentUnit);

        // Get the enrollmentUnit
        restEnrollmentUnitMockMvc.perform(get("/api/enrollment-units/{id}", enrollmentUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enrollmentUnit.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnrollmentUnit() throws Exception {
        // Get the enrollmentUnit
        restEnrollmentUnitMockMvc.perform(get("/api/enrollment-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnrollmentUnit() throws Exception {
        // Initialize the database
        enrollmentUnitRepository.saveAndFlush(enrollmentUnit);

        int databaseSizeBeforeUpdate = enrollmentUnitRepository.findAll().size();

        // Update the enrollmentUnit
        EnrollmentUnit updatedEnrollmentUnit = enrollmentUnitRepository.findById(enrollmentUnit.getId()).get();
        // Disconnect from session so that the updates on updatedEnrollmentUnit are not directly saved in db
        em.detach(updatedEnrollmentUnit);
        updatedEnrollmentUnit
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        EnrollmentUnitDTO enrollmentUnitDTO = enrollmentUnitMapper.toDto(updatedEnrollmentUnit);

        restEnrollmentUnitMockMvc.perform(put("/api/enrollment-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentUnitDTO)))
            .andExpect(status().isOk());

        // Validate the EnrollmentUnit in the database
        List<EnrollmentUnit> enrollmentUnitList = enrollmentUnitRepository.findAll();
        assertThat(enrollmentUnitList).hasSize(databaseSizeBeforeUpdate);
        EnrollmentUnit testEnrollmentUnit = enrollmentUnitList.get(enrollmentUnitList.size() - 1);
        assertThat(testEnrollmentUnit.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEnrollmentUnit.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEnrollmentUnit() throws Exception {
        int databaseSizeBeforeUpdate = enrollmentUnitRepository.findAll().size();

        // Create the EnrollmentUnit
        EnrollmentUnitDTO enrollmentUnitDTO = enrollmentUnitMapper.toDto(enrollmentUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnrollmentUnitMockMvc.perform(put("/api/enrollment-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnrollmentUnit in the database
        List<EnrollmentUnit> enrollmentUnitList = enrollmentUnitRepository.findAll();
        assertThat(enrollmentUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnrollmentUnit() throws Exception {
        // Initialize the database
        enrollmentUnitRepository.saveAndFlush(enrollmentUnit);

        int databaseSizeBeforeDelete = enrollmentUnitRepository.findAll().size();

        // Delete the enrollmentUnit
        restEnrollmentUnitMockMvc.perform(delete("/api/enrollment-units/{id}", enrollmentUnit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EnrollmentUnit> enrollmentUnitList = enrollmentUnitRepository.findAll();
        assertThat(enrollmentUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrollmentUnit.class);
        EnrollmentUnit enrollmentUnit1 = new EnrollmentUnit();
        enrollmentUnit1.setId(1L);
        EnrollmentUnit enrollmentUnit2 = new EnrollmentUnit();
        enrollmentUnit2.setId(enrollmentUnit1.getId());
        assertThat(enrollmentUnit1).isEqualTo(enrollmentUnit2);
        enrollmentUnit2.setId(2L);
        assertThat(enrollmentUnit1).isNotEqualTo(enrollmentUnit2);
        enrollmentUnit1.setId(null);
        assertThat(enrollmentUnit1).isNotEqualTo(enrollmentUnit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrollmentUnitDTO.class);
        EnrollmentUnitDTO enrollmentUnitDTO1 = new EnrollmentUnitDTO();
        enrollmentUnitDTO1.setId(1L);
        EnrollmentUnitDTO enrollmentUnitDTO2 = new EnrollmentUnitDTO();
        assertThat(enrollmentUnitDTO1).isNotEqualTo(enrollmentUnitDTO2);
        enrollmentUnitDTO2.setId(enrollmentUnitDTO1.getId());
        assertThat(enrollmentUnitDTO1).isEqualTo(enrollmentUnitDTO2);
        enrollmentUnitDTO2.setId(2L);
        assertThat(enrollmentUnitDTO1).isNotEqualTo(enrollmentUnitDTO2);
        enrollmentUnitDTO1.setId(null);
        assertThat(enrollmentUnitDTO1).isNotEqualTo(enrollmentUnitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(enrollmentUnitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(enrollmentUnitMapper.fromId(null)).isNull();
    }
}
