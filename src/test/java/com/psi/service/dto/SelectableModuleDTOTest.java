package com.psi.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class SelectableModuleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SelectableModuleDTO.class);
        SelectableModuleDTO selectableModuleDTO1 = new SelectableModuleDTO();
        selectableModuleDTO1.setId(1L);
        SelectableModuleDTO selectableModuleDTO2 = new SelectableModuleDTO();
        assertThat(selectableModuleDTO1).isNotEqualTo(selectableModuleDTO2);
        selectableModuleDTO2.setId(selectableModuleDTO1.getId());
        assertThat(selectableModuleDTO1).isEqualTo(selectableModuleDTO2);
        selectableModuleDTO2.setId(2L);
        assertThat(selectableModuleDTO1).isNotEqualTo(selectableModuleDTO2);
        selectableModuleDTO1.setId(null);
        assertThat(selectableModuleDTO1).isNotEqualTo(selectableModuleDTO2);
    }
}
