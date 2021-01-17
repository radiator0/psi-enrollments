package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.FieldOfStudy;
import com.psi.domain.StudyProgram;
import com.psi.repository.FieldOfStudyRepository;
import com.psi.service.FieldOfStudyService;
import com.psi.service.dto.FieldOfStudyDTO;
import com.psi.service.mapper.FieldOfStudyMapper;

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
 * Integration tests for the {@link FieldOfStudyResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FieldOfStudyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UNIQUE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UNIQUE_NAME = "BBBBBBBBBB";

    @Autowired
    private FieldOfStudyRepository fieldOfStudyRepository;

    @Autowired
    private FieldOfStudyMapper fieldOfStudyMapper;

    @Autowired
    private FieldOfStudyService fieldOfStudyService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldOfStudyMockMvc;

    private FieldOfStudy fieldOfStudy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldOfStudy createEntity(EntityManager em) {
        FieldOfStudy fieldOfStudy = new FieldOfStudy()
            .name(DEFAULT_NAME)
            .uniqueName(DEFAULT_UNIQUE_NAME);
        // Add required entity
        StudyProgram studyProgram;
        if (TestUtil.findAll(em, StudyProgram.class).isEmpty()) {
            studyProgram = StudyProgramResourceIT.createEntity(em);
            em.persist(studyProgram);
            em.flush();
        } else {
            studyProgram = TestUtil.findAll(em, StudyProgram.class).get(0);
        }
        fieldOfStudy.setStudyProgram(studyProgram);
        return fieldOfStudy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldOfStudy createUpdatedEntity(EntityManager em) {
        FieldOfStudy fieldOfStudy = new FieldOfStudy()
            .name(UPDATED_NAME)
            .uniqueName(UPDATED_UNIQUE_NAME);
        // Add required entity
        StudyProgram studyProgram;
        if (TestUtil.findAll(em, StudyProgram.class).isEmpty()) {
            studyProgram = StudyProgramResourceIT.createUpdatedEntity(em);
            em.persist(studyProgram);
            em.flush();
        } else {
            studyProgram = TestUtil.findAll(em, StudyProgram.class).get(0);
        }
        fieldOfStudy.setStudyProgram(studyProgram);
        return fieldOfStudy;
    }

    @BeforeEach
    public void initTest() {
        fieldOfStudy = createEntity(em);
    }

    @Test
    @Transactional
    public void createFieldOfStudy() throws Exception {
        int databaseSizeBeforeCreate = fieldOfStudyRepository.findAll().size();
        // Create the FieldOfStudy
        FieldOfStudyDTO fieldOfStudyDTO = fieldOfStudyMapper.toDto(fieldOfStudy);
        restFieldOfStudyMockMvc.perform(post("/api/field-of-studies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldOfStudyDTO)))
            .andExpect(status().isCreated());

        // Validate the FieldOfStudy in the database
        List<FieldOfStudy> fieldOfStudyList = fieldOfStudyRepository.findAll();
        assertThat(fieldOfStudyList).hasSize(databaseSizeBeforeCreate + 1);
        FieldOfStudy testFieldOfStudy = fieldOfStudyList.get(fieldOfStudyList.size() - 1);
        assertThat(testFieldOfStudy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFieldOfStudy.getUniqueName()).isEqualTo(DEFAULT_UNIQUE_NAME);
    }

    @Test
    @Transactional
    public void createFieldOfStudyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fieldOfStudyRepository.findAll().size();

        // Create the FieldOfStudy with an existing ID
        fieldOfStudy.setId(1L);
        FieldOfStudyDTO fieldOfStudyDTO = fieldOfStudyMapper.toDto(fieldOfStudy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldOfStudyMockMvc.perform(post("/api/field-of-studies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldOfStudyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FieldOfStudy in the database
        List<FieldOfStudy> fieldOfStudyList = fieldOfStudyRepository.findAll();
        assertThat(fieldOfStudyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldOfStudyRepository.findAll().size();
        // set the field null
        fieldOfStudy.setName(null);

        // Create the FieldOfStudy, which fails.
        FieldOfStudyDTO fieldOfStudyDTO = fieldOfStudyMapper.toDto(fieldOfStudy);


        restFieldOfStudyMockMvc.perform(post("/api/field-of-studies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldOfStudyDTO)))
            .andExpect(status().isBadRequest());

        List<FieldOfStudy> fieldOfStudyList = fieldOfStudyRepository.findAll();
        assertThat(fieldOfStudyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUniqueNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldOfStudyRepository.findAll().size();
        // set the field null
        fieldOfStudy.setUniqueName(null);

        // Create the FieldOfStudy, which fails.
        FieldOfStudyDTO fieldOfStudyDTO = fieldOfStudyMapper.toDto(fieldOfStudy);


        restFieldOfStudyMockMvc.perform(post("/api/field-of-studies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldOfStudyDTO)))
            .andExpect(status().isBadRequest());

        List<FieldOfStudy> fieldOfStudyList = fieldOfStudyRepository.findAll();
        assertThat(fieldOfStudyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFieldOfStudies() throws Exception {
        // Initialize the database
        fieldOfStudyRepository.saveAndFlush(fieldOfStudy);

        // Get all the fieldOfStudyList
        restFieldOfStudyMockMvc.perform(get("/api/field-of-studies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldOfStudy.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uniqueName").value(hasItem(DEFAULT_UNIQUE_NAME)));
    }
    
    @Test
    @Transactional
    public void getFieldOfStudy() throws Exception {
        // Initialize the database
        fieldOfStudyRepository.saveAndFlush(fieldOfStudy);

        // Get the fieldOfStudy
        restFieldOfStudyMockMvc.perform(get("/api/field-of-studies/{id}", fieldOfStudy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldOfStudy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.uniqueName").value(DEFAULT_UNIQUE_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingFieldOfStudy() throws Exception {
        // Get the fieldOfStudy
        restFieldOfStudyMockMvc.perform(get("/api/field-of-studies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFieldOfStudy() throws Exception {
        // Initialize the database
        fieldOfStudyRepository.saveAndFlush(fieldOfStudy);

        int databaseSizeBeforeUpdate = fieldOfStudyRepository.findAll().size();

        // Update the fieldOfStudy
        FieldOfStudy updatedFieldOfStudy = fieldOfStudyRepository.findById(fieldOfStudy.getId()).get();
        // Disconnect from session so that the updates on updatedFieldOfStudy are not directly saved in db
        em.detach(updatedFieldOfStudy);
        updatedFieldOfStudy
            .name(UPDATED_NAME)
            .uniqueName(UPDATED_UNIQUE_NAME);
        FieldOfStudyDTO fieldOfStudyDTO = fieldOfStudyMapper.toDto(updatedFieldOfStudy);

        restFieldOfStudyMockMvc.perform(put("/api/field-of-studies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldOfStudyDTO)))
            .andExpect(status().isOk());

        // Validate the FieldOfStudy in the database
        List<FieldOfStudy> fieldOfStudyList = fieldOfStudyRepository.findAll();
        assertThat(fieldOfStudyList).hasSize(databaseSizeBeforeUpdate);
        FieldOfStudy testFieldOfStudy = fieldOfStudyList.get(fieldOfStudyList.size() - 1);
        assertThat(testFieldOfStudy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFieldOfStudy.getUniqueName()).isEqualTo(UPDATED_UNIQUE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFieldOfStudy() throws Exception {
        int databaseSizeBeforeUpdate = fieldOfStudyRepository.findAll().size();

        // Create the FieldOfStudy
        FieldOfStudyDTO fieldOfStudyDTO = fieldOfStudyMapper.toDto(fieldOfStudy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldOfStudyMockMvc.perform(put("/api/field-of-studies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldOfStudyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FieldOfStudy in the database
        List<FieldOfStudy> fieldOfStudyList = fieldOfStudyRepository.findAll();
        assertThat(fieldOfStudyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFieldOfStudy() throws Exception {
        // Initialize the database
        fieldOfStudyRepository.saveAndFlush(fieldOfStudy);

        int databaseSizeBeforeDelete = fieldOfStudyRepository.findAll().size();

        // Delete the fieldOfStudy
        restFieldOfStudyMockMvc.perform(delete("/api/field-of-studies/{id}", fieldOfStudy.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FieldOfStudy> fieldOfStudyList = fieldOfStudyRepository.findAll();
        assertThat(fieldOfStudyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
