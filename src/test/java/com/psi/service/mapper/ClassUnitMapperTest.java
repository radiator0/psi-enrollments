package com.psi.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClassUnitMapperTest {

    private ClassUnitMapper classUnitMapper;

    @BeforeEach
    public void setUp() {
        classUnitMapper = new ClassUnitMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(classUnitMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(classUnitMapper.fromId(null)).isNull();
    }
}
