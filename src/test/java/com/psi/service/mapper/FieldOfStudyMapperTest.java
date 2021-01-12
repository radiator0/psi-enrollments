package com.psi.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FieldOfStudyMapperTest {

    private FieldOfStudyMapper fieldOfStudyMapper;

    @BeforeEach
    public void setUp() {
        fieldOfStudyMapper = new FieldOfStudyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(fieldOfStudyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(fieldOfStudyMapper.fromId(null)).isNull();
    }
}
