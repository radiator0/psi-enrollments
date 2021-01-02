package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.Lecturer;
import com.psi.repository.LecturerRepository;
import com.psi.service.LecturerService;
import com.psi.service.dto.LecturerDTO;
import com.psi.service.mapper.LecturerMapper;
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
 * Integration tests for the {@link LecturerResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
public class LecturerResourceIT {

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private LecturerMapper lecturerMapper;

    @Autowired
    private LecturerService lecturerService;

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

    private MockMvc restLecturerMockMvc;

    private Lecturer lecturer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LecturerResource lecturerResource = new LecturerResource(lecturerService);
        this.restLecturerMockMvc = MockMvcBuilders.standaloneSetup(lecturerResource)
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
    public static Lecturer createEntity(EntityManager em) {
        Lecturer lecturer = new Lecturer();
        return lecturer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lecturer createUpdatedEntity(EntityManager em) {
        Lecturer lecturer = new Lecturer();
        return lecturer;
    }

    @BeforeEach
    public void initTest() {
        lecturer = createEntity(em);
    }


    @Test
    @Transactional
    public void createLecturerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lecturerRepository.findAll().size();

        // Create the Lecturer with an existing ID
        lecturer.setId(1L);
        LecturerDTO lecturerDTO = lecturerMapper.toDto(lecturer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLecturerMockMvc.perform(post("/api/lecturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lecturer in the database
        List<Lecturer> lecturerList = lecturerRepository.findAll();
        assertThat(lecturerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lecturerRepository.findAll().size();
        // set the field null
        lecturer.setFirstName(null);

        // Create the Lecturer, which fails.
        LecturerDTO lecturerDTO = lecturerMapper.toDto(lecturer);

        restLecturerMockMvc.perform(post("/api/lecturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturerDTO)))
            .andExpect(status().isBadRequest());

        List<Lecturer> lecturerList = lecturerRepository.findAll();
        assertThat(lecturerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lecturerRepository.findAll().size();
        // set the field null
        lecturer.setLastName(null);

        // Create the Lecturer, which fails.
        LecturerDTO lecturerDTO = lecturerMapper.toDto(lecturer);

        restLecturerMockMvc.perform(post("/api/lecturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturerDTO)))
            .andExpect(status().isBadRequest());

        List<Lecturer> lecturerList = lecturerRepository.findAll();
        assertThat(lecturerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMailIsRequired() throws Exception {
        int databaseSizeBeforeTest = lecturerRepository.findAll().size();
        // set the field null
        lecturer.setMail(null);

        // Create the Lecturer, which fails.
        LecturerDTO lecturerDTO = lecturerMapper.toDto(lecturer);

        restLecturerMockMvc.perform(post("/api/lecturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturerDTO)))
            .andExpect(status().isBadRequest());

        List<Lecturer> lecturerList = lecturerRepository.findAll();
        assertThat(lecturerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLecturers() throws Exception {
        // Initialize the database
        lecturerRepository.saveAndFlush(lecturer);

        // Get all the lecturerList
        restLecturerMockMvc.perform(get("/api/lecturers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    @Transactional
    public void getLecturer() throws Exception {
        // Initialize the database
        lecturerRepository.saveAndFlush(lecturer);

        // Get the lecturer
        restLecturerMockMvc.perform(get("/api/lecturers/{id}", lecturer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingLecturer() throws Exception {
        // Get the lecturer
        restLecturerMockMvc.perform(get("/api/lecturers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNonExistingLecturer() throws Exception {
        int databaseSizeBeforeUpdate = lecturerRepository.findAll().size();

        // Create the Lecturer
        LecturerDTO lecturerDTO = lecturerMapper.toDto(lecturer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLecturerMockMvc.perform(put("/api/lecturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lecturer in the database
        List<Lecturer> lecturerList = lecturerRepository.findAll();
        assertThat(lecturerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLecturer() throws Exception {
        // Initialize the database
        lecturerRepository.saveAndFlush(lecturer);

        int databaseSizeBeforeDelete = lecturerRepository.findAll().size();

        // Delete the lecturer
        restLecturerMockMvc.perform(delete("/api/lecturers/{id}", lecturer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lecturer> lecturerList = lecturerRepository.findAll();
        assertThat(lecturerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lecturer.class);
        Lecturer lecturer1 = new Lecturer();
        lecturer1.setId(1L);
        Lecturer lecturer2 = new Lecturer();
        lecturer2.setId(lecturer1.getId());
        assertThat(lecturer1).isEqualTo(lecturer2);
        lecturer2.setId(2L);
        assertThat(lecturer1).isNotEqualTo(lecturer2);
        lecturer1.setId(null);
        assertThat(lecturer1).isNotEqualTo(lecturer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LecturerDTO.class);
        LecturerDTO lecturerDTO1 = new LecturerDTO();
        lecturerDTO1.setId(1L);
        LecturerDTO lecturerDTO2 = new LecturerDTO();
        assertThat(lecturerDTO1).isNotEqualTo(lecturerDTO2);
        lecturerDTO2.setId(lecturerDTO1.getId());
        assertThat(lecturerDTO1).isEqualTo(lecturerDTO2);
        lecturerDTO2.setId(2L);
        assertThat(lecturerDTO1).isNotEqualTo(lecturerDTO2);
        lecturerDTO1.setId(null);
        assertThat(lecturerDTO1).isNotEqualTo(lecturerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lecturerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lecturerMapper.fromId(null)).isNull();
    }
}
