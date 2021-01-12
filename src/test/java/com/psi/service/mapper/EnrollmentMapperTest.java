package com.psi.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EnrollmentMapperTest {

    private EnrollmentMapper enrollmentMapper;

    @BeforeEach
    public void setUp() {
        enrollmentMapper = new EnrollmentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(enrollmentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(enrollmentMapper.fromId(null)).isNull();
    }
}
