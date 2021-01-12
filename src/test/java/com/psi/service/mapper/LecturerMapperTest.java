package com.psi.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LecturerMapperTest {

    private LecturerMapper lecturerMapper;

    @BeforeEach
    public void setUp() {
        lecturerMapper = new LecturerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(lecturerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(lecturerMapper.fromId(null)).isNull();
    }
}
