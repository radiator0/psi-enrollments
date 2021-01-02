package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.SelectableModule;
import com.psi.repository.SelectableModuleRepository;
import com.psi.service.SelectableModuleService;
import com.psi.service.dto.SelectableModuleDTO;
import com.psi.service.mapper.SelectableModuleMapper;
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
 * Integration tests for the {@link SelectableModuleResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
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
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSelectableModuleMockMvc;

    private SelectableModule selectableModule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SelectableModuleResource selectableModuleResource = new SelectableModuleResource(selectableModuleService);
        this.restSelectableModuleMockMvc = MockMvcBuilders.standaloneSetup(selectableModuleResource)
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
    public static SelectableModule createEntity(EntityManager em) {
        SelectableModule selectableModule = new SelectableModule()
            .name(DEFAULT_NAME);
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SelectableModule> selectableModuleList = selectableModuleRepository.findAll();
        assertThat(selectableModuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SelectableModule.class);
        SelectableModule selectableModule1 = new SelectableModule();
        selectableModule1.setId(1L);
        SelectableModule selectableModule2 = new SelectableModule();
        selectableModule2.setId(selectableModule1.getId());
        assertThat(selectableModule1).isEqualTo(selectableModule2);
        selectableModule2.setId(2L);
        assertThat(selectableModule1).isNotEqualTo(selectableModule2);
        selectableModule1.setId(null);
        assertThat(selectableModule1).isNotEqualTo(selectableModule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SelectableModuleDTO.class);
        SelectableModuleDTO selectableModuleDTO1 = new SelectableModuleDTO();
        selectableModuleDTO1.setId(1L);
        SelectableModuleDTO selectableModuleDTO2 = new SelectableModuleDTO();
        assertThat(selectableModuleDTO1).isNotEqualTo(selectableModuleDTO2);
        selectableModuleDTO2.setId(selectableModuleDTO1.getId());
        assertThat(selectableModuleDTO1).isEqualTo(selectableModuleDTO2);
        selectableModuleDTO2.setId(2L);
        assertThat(selectableModuleDTO1).isNotEqualTo(selectableModuleDTO2);
        selectableModuleDTO1.setId(null);
        assertThat(selectableModuleDTO1).isNotEqualTo(selectableModuleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(selectableModuleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(selectableModuleMapper.fromId(null)).isNull();
    }
}
