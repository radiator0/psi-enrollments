package com.psi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class FieldOfStudyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldOfStudy.class);
        FieldOfStudy fieldOfStudy1 = new FieldOfStudy();
        fieldOfStudy1.setId(1L);
        FieldOfStudy fieldOfStudy2 = new FieldOfStudy();
        fieldOfStudy2.setId(fieldOfStudy1.getId());
        assertThat(fieldOfStudy1).isEqualTo(fieldOfStudy2);
        fieldOfStudy2.setId(2L);
        assertThat(fieldOfStudy1).isNotEqualTo(fieldOfStudy2);
        fieldOfStudy1.setId(null);
        assertThat(fieldOfStudy1).isNotEqualTo(fieldOfStudy2);
    }
}
