package com.psi.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SelectableModuleMapperTest {

    private SelectableModuleMapper selectableModuleMapper;

    @BeforeEach
    public void setUp() {
        selectableModuleMapper = new SelectableModuleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(selectableModuleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(selectableModuleMapper.fromId(null)).isNull();
    }
}
