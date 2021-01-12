package com.psi.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClassScheduleMapperTest {

    private ClassScheduleMapper classScheduleMapper;

    @BeforeEach
    public void setUp() {
        classScheduleMapper = new ClassScheduleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(classScheduleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(classScheduleMapper.fromId(null)).isNull();
    }
}
