package com.psi.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClassGroupMapperTest {

    private ClassGroupMapper classGroupMapper;

    @BeforeEach
    public void setUp() {
        classGroupMapper = new ClassGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(classGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(classGroupMapper.fromId(null)).isNull();
    }
}
