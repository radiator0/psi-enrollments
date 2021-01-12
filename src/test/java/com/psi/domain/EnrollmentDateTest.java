package com.psi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class EnrollmentDateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrollmentDate.class);
        EnrollmentDate enrollmentDate1 = new EnrollmentDate();
        enrollmentDate1.setId(1L);
        EnrollmentDate enrollmentDate2 = new EnrollmentDate();
        enrollmentDate2.setId(enrollmentDate1.getId());
        assertThat(enrollmentDate1).isEqualTo(enrollmentDate2);
        enrollmentDate2.setId(2L);
        assertThat(enrollmentDate1).isNotEqualTo(enrollmentDate2);
        enrollmentDate1.setId(null);
        assertThat(enrollmentDate1).isNotEqualTo(enrollmentDate2);
    }
}
