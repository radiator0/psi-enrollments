package com.psi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class ClassGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassGroup.class);
        ClassGroup classGroup1 = new ClassGroup();
        classGroup1.setId(1L);
        ClassGroup classGroup2 = new ClassGroup();
        classGroup2.setId(classGroup1.getId());
        assertThat(classGroup1).isEqualTo(classGroup2);
        classGroup2.setId(2L);
        assertThat(classGroup1).isNotEqualTo(classGroup2);
        classGroup1.setId(null);
        assertThat(classGroup1).isNotEqualTo(classGroup2);
    }
}
