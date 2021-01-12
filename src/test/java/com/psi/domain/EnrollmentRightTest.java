package com.psi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class EnrollmentRightTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrollmentRight.class);
        EnrollmentRight enrollmentRight1 = new EnrollmentRight();
        enrollmentRight1.setId(1L);
        EnrollmentRight enrollmentRight2 = new EnrollmentRight();
        enrollmentRight2.setId(enrollmentRight1.getId());
        assertThat(enrollmentRight1).isEqualTo(enrollmentRight2);
        enrollmentRight2.setId(2L);
        assertThat(enrollmentRight1).isNotEqualTo(enrollmentRight2);
        enrollmentRight1.setId(null);
        assertThat(enrollmentRight1).isNotEqualTo(enrollmentRight2);
    }
}
