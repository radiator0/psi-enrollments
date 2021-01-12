package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.ClassUnit;
import com.psi.repository.ClassUnitRepository;
import com.psi.service.ClassUnitService;
import com.psi.service.dto.ClassUnitDTO;
import com.psi.service.mapper.ClassUnitMapper;

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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ClassUnitResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClassUnitResourceIT {

    private static final LocalDate DEFAULT_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAY = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ClassUnitRepository classUnitRepository;

    @Autowired
    private ClassUnitMapper classUnitMapper;

    @Autowired
    private ClassUnitService classUnitService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassUnitMockMvc;

    private ClassUnit classUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassUnit createEntity(EntityManager em) {
        ClassUnit classUnit = new ClassUnit()
            .day(DEFAULT_DAY)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        return classUnit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassUnit createUpdatedEntity(EntityManager em) {
        ClassUnit classUnit = new ClassUnit()
            .day(UPDATED_DAY)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        return classUnit;
    }

    @BeforeEach
    public void initTest() {
        classUnit = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassUnit() throws Exception {
        int databaseSizeBeforeCreate = classUnitRepository.findAll().size();
        // Create the ClassUnit
        ClassUnitDTO classUnitDTO = classUnitMapper.toDto(classUnit);
        restClassUnitMockMvc.perform(post("/api/class-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassUnit in the database
        List<ClassUnit> classUnitList = classUnitRepository.findAll();
        assertThat(classUnitList).hasSize(databaseSizeBeforeCreate + 1);
        ClassUnit testClassUnit = classUnitList.get(classUnitList.size() - 1);
        assertThat(testClassUnit.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testClassUnit.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testClassUnit.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    public void createClassUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classUnitRepository.findAll().size();

        // Create the ClassUnit with an existing ID
        classUnit.setId(1L);
        ClassUnitDTO classUnitDTO = classUnitMapper.toDto(classUnit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassUnitMockMvc.perform(post("/api/class-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassUnit in the database
        List<ClassUnit> classUnitList = classUnitRepository.findAll();
        assertThat(classUnitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClassUnits() throws Exception {
        // Initialize the database
        classUnitRepository.saveAndFlush(classUnit);

        // Get all the classUnitList
        restClassUnitMockMvc.perform(get("/api/class-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getClassUnit() throws Exception {
        // Initialize the database
        classUnitRepository.saveAndFlush(classUnit);

        // Get the classUnit
        restClassUnitMockMvc.perform(get("/api/class-units/{id}", classUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classUnit.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingClassUnit() throws Exception {
        // Get the classUnit
        restClassUnitMockMvc.perform(get("/api/class-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassUnit() throws Exception {
        // Initialize the database
        classUnitRepository.saveAndFlush(classUnit);

        int databaseSizeBeforeUpdate = classUnitRepository.findAll().size();

        // Update the classUnit
        ClassUnit updatedClassUnit = classUnitRepository.findById(classUnit.getId()).get();
        // Disconnect from session so that the updates on updatedClassUnit are not directly saved in db
        em.detach(updatedClassUnit);
        updatedClassUnit
            .day(UPDATED_DAY)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        ClassUnitDTO classUnitDTO = classUnitMapper.toDto(updatedClassUnit);

        restClassUnitMockMvc.perform(put("/api/class-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classUnitDTO)))
            .andExpect(status().isOk());

        // Validate the ClassUnit in the database
        List<ClassUnit> classUnitList = classUnitRepository.findAll();
        assertThat(classUnitList).hasSize(databaseSizeBeforeUpdate);
        ClassUnit testClassUnit = classUnitList.get(classUnitList.size() - 1);
        assertThat(testClassUnit.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testClassUnit.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testClassUnit.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingClassUnit() throws Exception {
        int databaseSizeBeforeUpdate = classUnitRepository.findAll().size();

        // Create the ClassUnit
        ClassUnitDTO classUnitDTO = classUnitMapper.toDto(classUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassUnitMockMvc.perform(put("/api/class-units")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassUnit in the database
        List<ClassUnit> classUnitList = classUnitRepository.findAll();
        assertThat(classUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClassUnit() throws Exception {
        // Initialize the database
        classUnitRepository.saveAndFlush(classUnit);

        int databaseSizeBeforeDelete = classUnitRepository.findAll().size();

        // Delete the classUnit
        restClassUnitMockMvc.perform(delete("/api/class-units/{id}", classUnit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassUnit> classUnitList = classUnitRepository.findAll();
        assertThat(classUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
