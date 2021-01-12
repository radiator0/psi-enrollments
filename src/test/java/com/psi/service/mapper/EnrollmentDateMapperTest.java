package com.psi.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EnrollmentDateMapperTest {

    private EnrollmentDateMapper enrollmentDateMapper;

    @BeforeEach
    public void setUp() {
        enrollmentDateMapper = new EnrollmentDateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(enrollmentDateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(enrollmentDateMapper.fromId(null)).isNull();
    }
}
