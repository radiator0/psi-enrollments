package com.psi.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EnrollmentRightMapperTest {

    private EnrollmentRightMapper enrollmentRightMapper;

    @BeforeEach
    public void setUp() {
        enrollmentRightMapper = new EnrollmentRightMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(enrollmentRightMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(enrollmentRightMapper.fromId(null)).isNull();
    }
}
