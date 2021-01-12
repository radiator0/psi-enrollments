package com.psi.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class ClassUnitDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassUnitDTO.class);
        ClassUnitDTO classUnitDTO1 = new ClassUnitDTO();
        classUnitDTO1.setId(1L);
        ClassUnitDTO classUnitDTO2 = new ClassUnitDTO();
        assertThat(classUnitDTO1).isNotEqualTo(classUnitDTO2);
        classUnitDTO2.setId(classUnitDTO1.getId());
        assertThat(classUnitDTO1).isEqualTo(classUnitDTO2);
        classUnitDTO2.setId(2L);
        assertThat(classUnitDTO1).isNotEqualTo(classUnitDTO2);
        classUnitDTO1.setId(null);
        assertThat(classUnitDTO1).isNotEqualTo(classUnitDTO2);
    }
}
