package com.psi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class ClassUnitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassUnit.class);
        ClassUnit classUnit1 = new ClassUnit();
        classUnit1.setId(1L);
        ClassUnit classUnit2 = new ClassUnit();
        classUnit2.setId(classUnit1.getId());
        assertThat(classUnit1).isEqualTo(classUnit2);
        classUnit2.setId(2L);
        assertThat(classUnit1).isNotEqualTo(classUnit2);
        classUnit1.setId(null);
        assertThat(classUnit1).isNotEqualTo(classUnit2);
    }
}
