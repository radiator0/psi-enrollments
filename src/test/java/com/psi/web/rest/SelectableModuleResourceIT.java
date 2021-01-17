package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.SelectableModule;
import com.psi.domain.CourseUnit;
import com.psi.repository.SelectableModuleRepository;
import com.psi.service.SelectableModuleService;
import com.psi.service.dto.SelectableModuleDTO;
import com.psi.service.mapper.SelectableModuleMapper;

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
 * Integration tests for the {@link SelectableModuleResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SelectableModuleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SelectableModuleRepository selectableModuleRepository;

    @Autowired
    private SelectableModuleMapper selectableModuleMapper;

    @Autowired
    private SelectableModuleService selectableModuleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSelectableModuleMockMvc;

    private SelectableModule selectableModule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SelectableModule createEntity(EntityManager em) {
        SelectableModule selectableModule = new SelectableModule()
            .name(DEFAULT_NAME);
        // Add required entity
        CourseUnit courseUnit;
        if (TestUtil.findAll(em, CourseUnit.class).isEmpty()) {
            courseUnit = CourseUnitResourceIT.createEntity(em);
            em.persist(courseUnit);
            em.flush();
        } else {
            courseUnit = TestUtil.findAll(em, CourseUnit.class).get(0);
        }
        selectableModule.getCourseUnits().add(courseUnit);
        return selectableModule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SelectableModule createUpdatedEntity(EntityManager em) {
        SelectableModule selectableModule = new SelectableModule()
            .name(UPDATED_NAME);
        // Add required entity
        CourseUnit courseUnit;
        if (TestUtil.findAll(em, CourseUnit.class).isEmpty()) {
            courseUnit = CourseUnitResourceIT.createUpdatedEntity(em);
            em.persist(courseUnit);
            em.flush();
        } else {
            courseUnit = TestUtil.findAll(em, CourseUnit.class).get(0);
        }
        selectableModule.getCourseUnits().add(courseUnit);
        return selectableModule;
    }

    @BeforeEach
    public void initTest() {
        selectableModule = createEntity(em);
    }

    @Test
    @Transactional
    public void createSelectableModule() throws Exception {
        int databaseSizeBeforeCreate = selectableModuleRepository.findAll().size();
        // Create the SelectableModule
        SelectableModuleDTO selectableModuleDTO = selectableModuleMapper.toDto(selectableModule);
        restSelectableModuleMockMvc.perform(post("/api/selectable-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(selectableModuleDTO)))
            .andExpect(status().isCreated());

        // Validate the SelectableModule in the database
        List<SelectableModule> selectableModuleList = selectableModuleRepository.findAll();
        assertThat(selectableModuleList).hasSize(databaseSizeBeforeCreate + 1);
        SelectableModule testSelectableModule = selectableModuleList.get(selectableModuleList.size() - 1);
        assertThat(testSelectableModule.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSelectableModuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = selectableModuleRepository.findAll().size();

        // Create the SelectableModule with an existing ID
        selectableModule.setId(1L);
        SelectableModuleDTO selectableModuleDTO = selectableModuleMapper.toDto(selectableModule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSelectableModuleMockMvc.perform(post("/api/selectable-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(selectableModuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SelectableModule in the database
        List<SelectableModule> selectableModuleList = selectableModuleRepository.findAll();
        assertThat(selectableModuleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSelectableModules() throws Exception {
        // Initialize the database
        selectableModuleRepository.saveAndFlush(selectableModule);

        // Get all the selectableModuleList
        restSelectableModuleMockMvc.perform(get("/api/selectable-modules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(selectableModule.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getSelectableModule() throws Exception {
        // Initialize the database
        selectableModuleRepository.saveAndFlush(selectableModule);

        // Get the selectableModule
        restSelectableModuleMockMvc.perform(get("/api/selectable-modules/{id}", selectableModule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(selectableModule.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingSelectableModule() throws Exception {
        // Get the selectableModule
        restSelectableModuleMockMvc.perform(get("/api/selectable-modules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSelectableModule() throws Exception {
        // Initialize the database
        selectableModuleRepository.saveAndFlush(selectableModule);

        int databaseSizeBeforeUpdate = selectableModuleRepository.findAll().size();

        // Update the selectableModule
        SelectableModule updatedSelectableModule = selectableModuleRepository.findById(selectableModule.getId()).get();
        // Disconnect from session so that the updates on updatedSelectableModule are not directly saved in db
        em.detach(updatedSelectableModule);
        updatedSelectableModule
            .name(UPDATED_NAME);
        SelectableModuleDTO selectableModuleDTO = selectableModuleMapper.toDto(updatedSelectableModule);

        restSelectableModuleMockMvc.perform(put("/api/selectable-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(selectableModuleDTO)))
            .andExpect(status().isOk());

        // Validate the SelectableModule in the database
        List<SelectableModule> selectableModuleList = selectableModuleRepository.findAll();
        assertThat(selectableModuleList).hasSize(databaseSizeBeforeUpdate);
        SelectableModule testSelectableModule = selectableModuleList.get(selectableModuleList.size() - 1);
        assertThat(testSelectableModule.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSelectableModule() throws Exception {
        int databaseSizeBeforeUpdate = selectableModuleRepository.findAll().size();

        // Create the SelectableModule
        SelectableModuleDTO selectableModuleDTO = selectableModuleMapper.toDto(selectableModule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSelectableModuleMockMvc.perform(put("/api/selectable-modules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(selectableModuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SelectableModule in the database
        List<SelectableModule> selectableModuleList = selectableModuleRepository.findAll();
        assertThat(selectableModuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSelectableModule() throws Exception {
        // Initialize the database
        selectableModuleRepository.saveAndFlush(selectableModule);

        int databaseSizeBeforeDelete = selectableModuleRepository.findAll().size();

        // Delete the selectableModule
        restSelectableModuleMockMvc.perform(delete("/api/selectable-modules/{id}", selectableModule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SelectableModule> selectableModuleList = selectableModuleRepository.findAll();
        assertThat(selectableModuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
