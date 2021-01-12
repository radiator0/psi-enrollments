package com.psi.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StudyProgramMapperTest {

    private StudyProgramMapper studyProgramMapper;

    @BeforeEach
    public void setUp() {
        studyProgramMapper = new StudyProgramMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(studyProgramMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(studyProgramMapper.fromId(null)).isNull();
    }
}
