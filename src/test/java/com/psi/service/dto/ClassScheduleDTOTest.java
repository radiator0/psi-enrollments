package com.psi.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class ClassScheduleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassScheduleDTO.class);
        ClassScheduleDTO classScheduleDTO1 = new ClassScheduleDTO();
        classScheduleDTO1.setId(1L);
        ClassScheduleDTO classScheduleDTO2 = new ClassScheduleDTO();
        assertThat(classScheduleDTO1).isNotEqualTo(classScheduleDTO2);
        classScheduleDTO2.setId(classScheduleDTO1.getId());
        assertThat(classScheduleDTO1).isEqualTo(classScheduleDTO2);
        classScheduleDTO2.setId(2L);
        assertThat(classScheduleDTO1).isNotEqualTo(classScheduleDTO2);
        classScheduleDTO1.setId(null);
        assertThat(classScheduleDTO1).isNotEqualTo(classScheduleDTO2);
    }
}
