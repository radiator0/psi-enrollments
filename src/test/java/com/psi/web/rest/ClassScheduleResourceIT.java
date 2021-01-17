package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.ClassSchedule;
import com.psi.domain.Lecturer;
import com.psi.domain.ClassGroup;
import com.psi.repository.ClassScheduleRepository;
import com.psi.service.ClassScheduleService;
import com.psi.service.dto.ClassScheduleDTO;
import com.psi.service.mapper.ClassScheduleMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.psi.domain.enumeration.DayOfWeek;
import com.psi.domain.enumeration.WeekType;
import com.psi.domain.enumeration.SemesterPeriod;
/**
 * Integration tests for the {@link ClassScheduleResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClassScheduleResourceIT {

    private static final DayOfWeek DEFAULT_DAY_OF_WEEK = DayOfWeek.Monday;
    private static final DayOfWeek UPDATED_DAY_OF_WEEK = DayOfWeek.Tuesday;

    private static final WeekType DEFAULT_WEEK_TYPE = WeekType.All;
    private static final WeekType UPDATED_WEEK_TYPE = WeekType.Even;

    private static final SemesterPeriod DEFAULT_SEMESTER_PERIOD = SemesterPeriod.Whole;
    private static final SemesterPeriod UPDATED_SEMESTER_PERIOD = SemesterPeriod.FirstHalf;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ClassScheduleRepository classScheduleRepository;

    @Autowired
    private ClassScheduleMapper classScheduleMapper;

    @Autowired
    private ClassScheduleService classScheduleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassScheduleMockMvc;

    private ClassSchedule classSchedule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassSchedule createEntity(EntityManager em) {
        ClassSchedule classSchedule = new ClassSchedule()
            .dayOfWeek(DEFAULT_DAY_OF_WEEK)
            .weekType(DEFAULT_WEEK_TYPE)
            .semesterPeriod(DEFAULT_SEMESTER_PERIOD)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        // Add required entity
        Lecturer lecturer;
        if (TestUtil.findAll(em, Lecturer.class).isEmpty()) {
            lecturer = LecturerResourceIT.createEntity(em);
            em.persist(lecturer);
            em.flush();
        } else {
            lecturer = TestUtil.findAll(em, Lecturer.class).get(0);
        }
        classSchedule.setLecturer(lecturer);
        // Add required entity
        ClassGroup classGroup;
        if (TestUtil.findAll(em, ClassGroup.class).isEmpty()) {
            classGroup = ClassGroupResourceIT.createEntity(em);
            em.persist(classGroup);
            em.flush();
        } else {
            classGroup = TestUtil.findAll(em, ClassGroup.class).get(0);
        }
        classSchedule.setClassGroup(classGroup);
        return classSchedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassSchedule createUpdatedEntity(EntityManager em) {
        ClassSchedule classSchedule = new ClassSchedule()
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .weekType(UPDATED_WEEK_TYPE)
            .semesterPeriod(UPDATED_SEMESTER_PERIOD)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        // Add required entity
        Lecturer lecturer;
        if (TestUtil.findAll(em, Lecturer.class).isEmpty()) {
            lecturer = LecturerResourceIT.createUpdatedEntity(em);
            em.persist(lecturer);
            em.flush();
        } else {
            lecturer = TestUtil.findAll(em, Lecturer.class).get(0);
        }
        classSchedule.setLecturer(lecturer);
        // Add required entity
        ClassGroup classGroup;
        if (TestUtil.findAll(em, ClassGroup.class).isEmpty()) {
            classGroup = ClassGroupResourceIT.createUpdatedEntity(em);
            em.persist(classGroup);
            em.flush();
        } else {
            classGroup = TestUtil.findAll(em, ClassGroup.class).get(0);
        }
        classSchedule.setClassGroup(classGroup);
        return classSchedule;
    }

    @BeforeEach
    public void initTest() {
        classSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassSchedule() throws Exception {
        int databaseSizeBeforeCreate = classScheduleRepository.findAll().size();
        // Create the ClassSchedule
        ClassScheduleDTO classScheduleDTO = classScheduleMapper.toDto(classSchedule);
        restClassScheduleMockMvc.perform(post("/api/class-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classScheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassSchedule in the database
        List<ClassSchedule> classScheduleList = classScheduleRepository.findAll();
        assertThat(classScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        ClassSchedule testClassSchedule = classScheduleList.get(classScheduleList.size() - 1);
        assertThat(testClassSchedule.getDayOfWeek()).isEqualTo(DEFAULT_DAY_OF_WEEK);
        assertThat(testClassSchedule.getWeekType()).isEqualTo(DEFAULT_WEEK_TYPE);
        assertThat(testClassSchedule.getSemesterPeriod()).isEqualTo(DEFAULT_SEMESTER_PERIOD);
        assertThat(testClassSchedule.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testClassSchedule.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    public void createClassScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classScheduleRepository.findAll().size();

        // Create the ClassSchedule with an existing ID
        classSchedule.setId(1L);
        ClassScheduleDTO classScheduleDTO = classScheduleMapper.toDto(classSchedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassScheduleMockMvc.perform(post("/api/class-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassSchedule in the database
        List<ClassSchedule> classScheduleList = classScheduleRepository.findAll();
        assertThat(classScheduleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDayOfWeekIsRequired() throws Exception {
        int databaseSizeBeforeTest = classScheduleRepository.findAll().size();
        // set the field null
        classSchedule.setDayOfWeek(null);

        // Create the ClassSchedule, which fails.
        ClassScheduleDTO classScheduleDTO = classScheduleMapper.toDto(classSchedule);


        restClassScheduleMockMvc.perform(post("/api/class-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classScheduleDTO)))
            .andExpect(status().isBadRequest());

        List<ClassSchedule> classScheduleList = classScheduleRepository.findAll();
        assertThat(classScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = classScheduleRepository.findAll().size();
        // set the field null
        classSchedule.setStartTime(null);

        // Create the ClassSchedule, which fails.
        ClassScheduleDTO classScheduleDTO = classScheduleMapper.toDto(classSchedule);


        restClassScheduleMockMvc.perform(post("/api/class-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classScheduleDTO)))
            .andExpect(status().isBadRequest());

        List<ClassSchedule> classScheduleList = classScheduleRepository.findAll();
        assertThat(classScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = classScheduleRepository.findAll().size();
        // set the field null
        classSchedule.setEndTime(null);

        // Create the ClassSchedule, which fails.
        ClassScheduleDTO classScheduleDTO = classScheduleMapper.toDto(classSchedule);


        restClassScheduleMockMvc.perform(post("/api/class-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classScheduleDTO)))
            .andExpect(status().isBadRequest());

        List<ClassSchedule> classScheduleList = classScheduleRepository.findAll();
        assertThat(classScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClassSchedules() throws Exception {
        // Initialize the database
        classScheduleRepository.saveAndFlush(classSchedule);

        // Get all the classScheduleList
        restClassScheduleMockMvc.perform(get("/api/class-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK.toString())))
            .andExpect(jsonPath("$.[*].weekType").value(hasItem(DEFAULT_WEEK_TYPE.toString())))
            .andExpect(jsonPath("$.[*].semesterPeriod").value(hasItem(DEFAULT_SEMESTER_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getClassSchedule() throws Exception {
        // Initialize the database
        classScheduleRepository.saveAndFlush(classSchedule);

        // Get the classSchedule
        restClassScheduleMockMvc.perform(get("/api/class-schedules/{id}", classSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classSchedule.getId().intValue()))
            .andExpect(jsonPath("$.dayOfWeek").value(DEFAULT_DAY_OF_WEEK.toString()))
            .andExpect(jsonPath("$.weekType").value(DEFAULT_WEEK_TYPE.toString()))
            .andExpect(jsonPath("$.semesterPeriod").value(DEFAULT_SEMESTER_PERIOD.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingClassSchedule() throws Exception {
        // Get the classSchedule
        restClassScheduleMockMvc.perform(get("/api/class-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassSchedule() throws Exception {
        // Initialize the database
        classScheduleRepository.saveAndFlush(classSchedule);

        int databaseSizeBeforeUpdate = classScheduleRepository.findAll().size();

        // Update the classSchedule
        ClassSchedule updatedClassSchedule = classScheduleRepository.findById(classSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedClassSchedule are not directly saved in db
        em.detach(updatedClassSchedule);
        updatedClassSchedule
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .weekType(UPDATED_WEEK_TYPE)
            .semesterPeriod(UPDATED_SEMESTER_PERIOD)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        ClassScheduleDTO classScheduleDTO = classScheduleMapper.toDto(updatedClassSchedule);

        restClassScheduleMockMvc.perform(put("/api/class-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classScheduleDTO)))
            .andExpect(status().isOk());

        // Validate the ClassSchedule in the database
        List<ClassSchedule> classScheduleList = classScheduleRepository.findAll();
        assertThat(classScheduleList).hasSize(databaseSizeBeforeUpdate);
        ClassSchedule testClassSchedule = classScheduleList.get(classScheduleList.size() - 1);
        assertThat(testClassSchedule.getDayOfWeek()).isEqualTo(UPDATED_DAY_OF_WEEK);
        assertThat(testClassSchedule.getWeekType()).isEqualTo(UPDATED_WEEK_TYPE);
        assertThat(testClassSchedule.getSemesterPeriod()).isEqualTo(UPDATED_SEMESTER_PERIOD);
        assertThat(testClassSchedule.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testClassSchedule.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingClassSchedule() throws Exception {
        int databaseSizeBeforeUpdate = classScheduleRepository.findAll().size();

        // Create the ClassSchedule
        ClassScheduleDTO classScheduleDTO = classScheduleMapper.toDto(classSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassScheduleMockMvc.perform(put("/api/class-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassSchedule in the database
        List<ClassSchedule> classScheduleList = classScheduleRepository.findAll();
        assertThat(classScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClassSchedule() throws Exception {
        // Initialize the database
        classScheduleRepository.saveAndFlush(classSchedule);

        int databaseSizeBeforeDelete = classScheduleRepository.findAll().size();

        // Delete the classSchedule
        restClassScheduleMockMvc.perform(delete("/api/class-schedules/{id}", classSchedule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassSchedule> classScheduleList = classScheduleRepository.findAll();
        assertThat(classScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
