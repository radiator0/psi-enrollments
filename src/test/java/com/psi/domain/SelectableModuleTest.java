package com.psi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class SelectableModuleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SelectableModule.class);
        SelectableModule selectableModule1 = new SelectableModule();
        selectableModule1.setId(1L);
        SelectableModule selectableModule2 = new SelectableModule();
        selectableModule2.setId(selectableModule1.getId());
        assertThat(selectableModule1).isEqualTo(selectableModule2);
        selectableModule2.setId(2L);
        assertThat(selectableModule1).isNotEqualTo(selectableModule2);
        selectableModule1.setId(null);
        assertThat(selectableModule1).isNotEqualTo(selectableModule2);
    }
}
