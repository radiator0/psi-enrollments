package com.psi.web.rest;

import com.psi.EnrollmentsApp;
import com.psi.domain.Request;
import com.psi.domain.ClassGroup;
import com.psi.domain.Student;
import com.psi.repository.RequestRepository;
import com.psi.service.RequestService;
import com.psi.service.dto.RequestDTO;
import com.psi.service.mapper.RequestMapper;

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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RequestResource} REST controller.
 */
@SpringBootTest(classes = EnrollmentsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RequestResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_EXAMINED = false;
    private static final Boolean UPDATED_IS_EXAMINED = true;
    private static final Boolean DEFAULT_IS_ACCEPTED = false;
    private static final Boolean UPDATED_IS_ACCEPTED = true;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private RequestService requestService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestMockMvc;

    private Request request;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Request createEntity(EntityManager em) {
        Request request = new Request()
            .uuid(DEFAULT_UUID)
            .date(DEFAULT_DATE)
            .text(DEFAULT_TEXT)
            .isExamined(DEFAULT_IS_EXAMINED)
            .setIsAccepted(DEFAULT_IS_ACCEPTED);
        // Add required entity
        ClassGroup classGroup;
        if (TestUtil.findAll(em, ClassGroup.class).isEmpty()) {
            classGroup = ClassGroupResourceIT.createEntity(em);
            em.persist(classGroup);
            em.flush();
        } else {
            classGroup = TestUtil.findAll(em, ClassGroup.class).get(0);
        }
        request.setClassGroup(classGroup);
        // Add required entity
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            student = StudentResourceIT.createEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        request.setStudent(student);
        return request;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Request createUpdatedEntity(EntityManager em) {
        Request request = new Request()
            .uuid(UPDATED_UUID)
            .date(UPDATED_DATE)
            .text(UPDATED_TEXT)
            .isExamined(UPDATED_IS_EXAMINED);
        // Add required entity
        ClassGroup classGroup;
        if (TestUtil.findAll(em, ClassGroup.class).isEmpty()) {
            classGroup = ClassGroupResourceIT.createUpdatedEntity(em);
            em.persist(classGroup);
            em.flush();
        } else {
            classGroup = TestUtil.findAll(em, ClassGroup.class).get(0);
        }
        request.setClassGroup(classGroup);
        // Add required entity
        Student student;
        if (TestUtil.findAll(em, Student.class).isEmpty()) {
            student = StudentResourceIT.createUpdatedEntity(em);
            em.persist(student);
            em.flush();
        } else {
            student = TestUtil.findAll(em, Student.class).get(0);
        }
        request.setStudent(student);
        return request;
    }

    @BeforeEach
    public void initTest() {
        request = createEntity(em);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = requestRepository.findAll().size();
        // set the field null
        request.setText(null);

        // Create the Request, which fails.
        RequestDTO requestDTO = requestMapper.toDto(request);


        restRequestMockMvc.perform(post("/api/requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(requestDTO)))
            .andExpect(status().isBadRequest());

        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getNonExistingRequest() throws Exception {
        // Get the request
        restRequestMockMvc.perform(get("/api/requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

}
