package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.Specialty;
import com.psi.repository.SpecialtyRepository;
import com.psi.service.SpecialtyService;
import com.psi.service.dto.SpecialtyDTO;
import com.psi.service.mapper.SpecialtyMapper;
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
 * Integration tests for the {@link SpecialtyResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
public class SpecialtyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private SpecialtyMapper specialtyMapper;

    @Autowired
    private SpecialtyService specialtyService;

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

    private MockMvc restSpecialtyMockMvc;

    private Specialty specialty;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpecialtyResource specialtyResource = new SpecialtyResource(specialtyService);
        this.restSpecialtyMockMvc = MockMvcBuilders.standaloneSetup(specialtyResource)
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
    public static Specialty createEntity(EntityManager em) {
        Specialty specialty = new Specialty()
            .name(DEFAULT_NAME);
        return specialty;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specialty createUpdatedEntity(EntityManager em) {
        Specialty specialty = new Specialty()
            .name(UPDATED_NAME);
        return specialty;
    }

    @BeforeEach
    public void initTest() {
        specialty = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpecialty() throws Exception {
        int databaseSizeBeforeCreate = specialtyRepository.findAll().size();

        // Create the Specialty
        SpecialtyDTO specialtyDTO = specialtyMapper.toDto(specialty);
        restSpecialtyMockMvc.perform(post("/api/specialties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialtyDTO)))
            .andExpect(status().isCreated());

        // Validate the Specialty in the database
        List<Specialty> specialtyList = specialtyRepository.findAll();
        assertThat(specialtyList).hasSize(databaseSizeBeforeCreate + 1);
        Specialty testSpecialty = specialtyList.get(specialtyList.size() - 1);
        assertThat(testSpecialty.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSpecialtyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specialtyRepository.findAll().size();

        // Create the Specialty with an existing ID
        specialty.setId(1L);
        SpecialtyDTO specialtyDTO = specialtyMapper.toDto(specialty);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialtyMockMvc.perform(post("/api/specialties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialtyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Specialty in the database
        List<Specialty> specialtyList = specialtyRepository.findAll();
        assertThat(specialtyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialtyRepository.findAll().size();
        // set the field null
        specialty.setName(null);

        // Create the Specialty, which fails.
        SpecialtyDTO specialtyDTO = specialtyMapper.toDto(specialty);

        restSpecialtyMockMvc.perform(post("/api/specialties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialtyDTO)))
            .andExpect(status().isBadRequest());

        List<Specialty> specialtyList = specialtyRepository.findAll();
        assertThat(specialtyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpecialties() throws Exception {
        // Initialize the database
        specialtyRepository.saveAndFlush(specialty);

        // Get all the specialtyList
        restSpecialtyMockMvc.perform(get("/api/specialties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialty.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getSpecialty() throws Exception {
        // Initialize the database
        specialtyRepository.saveAndFlush(specialty);

        // Get the specialty
        restSpecialtyMockMvc.perform(get("/api/specialties/{id}", specialty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(specialty.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingSpecialty() throws Exception {
        // Get the specialty
        restSpecialtyMockMvc.perform(get("/api/specialties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecialty() throws Exception {
        // Initialize the database
        specialtyRepository.saveAndFlush(specialty);

        int databaseSizeBeforeUpdate = specialtyRepository.findAll().size();

        // Update the specialty
        Specialty updatedSpecialty = specialtyRepository.findById(specialty.getId()).get();
        // Disconnect from session so that the updates on updatedSpecialty are not directly saved in db
        em.detach(updatedSpecialty);
        updatedSpecialty
            .name(UPDATED_NAME);
        SpecialtyDTO specialtyDTO = specialtyMapper.toDto(updatedSpecialty);

        restSpecialtyMockMvc.perform(put("/api/specialties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialtyDTO)))
            .andExpect(status().isOk());

        // Validate the Specialty in the database
        List<Specialty> specialtyList = specialtyRepository.findAll();
        assertThat(specialtyList).hasSize(databaseSizeBeforeUpdate);
        Specialty testSpecialty = specialtyList.get(specialtyList.size() - 1);
        assertThat(testSpecialty.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSpecialty() throws Exception {
        int databaseSizeBeforeUpdate = specialtyRepository.findAll().size();

        // Create the Specialty
        SpecialtyDTO specialtyDTO = specialtyMapper.toDto(specialty);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialtyMockMvc.perform(put("/api/specialties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialtyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Specialty in the database
        List<Specialty> specialtyList = specialtyRepository.findAll();
        assertThat(specialtyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpecialty() throws Exception {
        // Initialize the database
        specialtyRepository.saveAndFlush(specialty);

        int databaseSizeBeforeDelete = specialtyRepository.findAll().size();

        // Delete the specialty
        restSpecialtyMockMvc.perform(delete("/api/specialties/{id}", specialty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Specialty> specialtyList = specialtyRepository.findAll();
        assertThat(specialtyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Specialty.class);
        Specialty specialty1 = new Specialty();
        specialty1.setId(1L);
        Specialty specialty2 = new Specialty();
        specialty2.setId(specialty1.getId());
        assertThat(specialty1).isEqualTo(specialty2);
        specialty2.setId(2L);
        assertThat(specialty1).isNotEqualTo(specialty2);
        specialty1.setId(null);
        assertThat(specialty1).isNotEqualTo(specialty2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialtyDTO.class);
        SpecialtyDTO specialtyDTO1 = new SpecialtyDTO();
        specialtyDTO1.setId(1L);
        SpecialtyDTO specialtyDTO2 = new SpecialtyDTO();
        assertThat(specialtyDTO1).isNotEqualTo(specialtyDTO2);
        specialtyDTO2.setId(specialtyDTO1.getId());
        assertThat(specialtyDTO1).isEqualTo(specialtyDTO2);
        specialtyDTO2.setId(2L);
        assertThat(specialtyDTO1).isNotEqualTo(specialtyDTO2);
        specialtyDTO1.setId(null);
        assertThat(specialtyDTO1).isNotEqualTo(specialtyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(specialtyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(specialtyMapper.fromId(null)).isNull();
    }
}
