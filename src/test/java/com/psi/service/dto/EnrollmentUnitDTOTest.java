package com.psi.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class EnrollmentUnitDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrollmentUnitDTO.class);
        EnrollmentUnitDTO enrollmentUnitDTO1 = new EnrollmentUnitDTO();
        enrollmentUnitDTO1.setId(1L);
        EnrollmentUnitDTO enrollmentUnitDTO2 = new EnrollmentUnitDTO();
        assertThat(enrollmentUnitDTO1).isNotEqualTo(enrollmentUnitDTO2);
        enrollmentUnitDTO2.setId(enrollmentUnitDTO1.getId());
        assertThat(enrollmentUnitDTO1).isEqualTo(enrollmentUnitDTO2);
        enrollmentUnitDTO2.setId(2L);
        assertThat(enrollmentUnitDTO1).isNotEqualTo(enrollmentUnitDTO2);
        enrollmentUnitDTO1.setId(null);
        assertThat(enrollmentUnitDTO1).isNotEqualTo(enrollmentUnitDTO2);
    }
}
