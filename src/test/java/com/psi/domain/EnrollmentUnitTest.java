package com.psi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class EnrollmentUnitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrollmentUnit.class);
        EnrollmentUnit enrollmentUnit1 = new EnrollmentUnit();
        enrollmentUnit1.setId(1L);
        EnrollmentUnit enrollmentUnit2 = new EnrollmentUnit();
        enrollmentUnit2.setId(enrollmentUnit1.getId());
        assertThat(enrollmentUnit1).isEqualTo(enrollmentUnit2);
        enrollmentUnit2.setId(2L);
        assertThat(enrollmentUnit1).isNotEqualTo(enrollmentUnit2);
        enrollmentUnit1.setId(null);
        assertThat(enrollmentUnit1).isNotEqualTo(enrollmentUnit2);
    }
}
