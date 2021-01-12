package com.psi.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class EnrollmentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrollmentDTO.class);
        EnrollmentDTO enrollmentDTO1 = new EnrollmentDTO();
        enrollmentDTO1.setId(1L);
        EnrollmentDTO enrollmentDTO2 = new EnrollmentDTO();
        assertThat(enrollmentDTO1).isNotEqualTo(enrollmentDTO2);
        enrollmentDTO2.setId(enrollmentDTO1.getId());
        assertThat(enrollmentDTO1).isEqualTo(enrollmentDTO2);
        enrollmentDTO2.setId(2L);
        assertThat(enrollmentDTO1).isNotEqualTo(enrollmentDTO2);
        enrollmentDTO1.setId(null);
        assertThat(enrollmentDTO1).isNotEqualTo(enrollmentDTO2);
    }
}
