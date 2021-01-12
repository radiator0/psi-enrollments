package com.psi.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class EnrollmentDateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrollmentDateDTO.class);
        EnrollmentDateDTO enrollmentDateDTO1 = new EnrollmentDateDTO();
        enrollmentDateDTO1.setId(1L);
        EnrollmentDateDTO enrollmentDateDTO2 = new EnrollmentDateDTO();
        assertThat(enrollmentDateDTO1).isNotEqualTo(enrollmentDateDTO2);
        enrollmentDateDTO2.setId(enrollmentDateDTO1.getId());
        assertThat(enrollmentDateDTO1).isEqualTo(enrollmentDateDTO2);
        enrollmentDateDTO2.setId(2L);
        assertThat(enrollmentDateDTO1).isNotEqualTo(enrollmentDateDTO2);
        enrollmentDateDTO1.setId(null);
        assertThat(enrollmentDateDTO1).isNotEqualTo(enrollmentDateDTO2);
    }
}
