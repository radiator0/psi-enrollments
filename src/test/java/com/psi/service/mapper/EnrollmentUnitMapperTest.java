package com.psi.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EnrollmentUnitMapperTest {

    private EnrollmentUnitMapper enrollmentUnitMapper;

    @BeforeEach
    public void setUp() {
        enrollmentUnitMapper = new EnrollmentUnitMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(enrollmentUnitMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(enrollmentUnitMapper.fromId(null)).isNull();
    }
}
