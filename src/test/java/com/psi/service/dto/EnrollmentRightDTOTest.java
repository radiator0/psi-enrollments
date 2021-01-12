package com.psi.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class EnrollmentRightDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrollmentRightDTO.class);
        EnrollmentRightDTO enrollmentRightDTO1 = new EnrollmentRightDTO();
        enrollmentRightDTO1.setId(1L);
        EnrollmentRightDTO enrollmentRightDTO2 = new EnrollmentRightDTO();
        assertThat(enrollmentRightDTO1).isNotEqualTo(enrollmentRightDTO2);
        enrollmentRightDTO2.setId(enrollmentRightDTO1.getId());
        assertThat(enrollmentRightDTO1).isEqualTo(enrollmentRightDTO2);
        enrollmentRightDTO2.setId(2L);
        assertThat(enrollmentRightDTO1).isNotEqualTo(enrollmentRightDTO2);
        enrollmentRightDTO1.setId(null);
        assertThat(enrollmentRightDTO1).isNotEqualTo(enrollmentRightDTO2);
    }
}
