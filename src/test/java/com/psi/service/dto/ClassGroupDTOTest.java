package com.psi.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class ClassGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassGroupDTO.class);
        ClassGroupDTO classGroupDTO1 = new ClassGroupDTO();
        classGroupDTO1.setId(1L);
        ClassGroupDTO classGroupDTO2 = new ClassGroupDTO();
        assertThat(classGroupDTO1).isNotEqualTo(classGroupDTO2);
        classGroupDTO2.setId(classGroupDTO1.getId());
        assertThat(classGroupDTO1).isEqualTo(classGroupDTO2);
        classGroupDTO2.setId(2L);
        assertThat(classGroupDTO1).isNotEqualTo(classGroupDTO2);
        classGroupDTO1.setId(null);
        assertThat(classGroupDTO1).isNotEqualTo(classGroupDTO2);
    }
}
