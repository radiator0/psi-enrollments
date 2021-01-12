package com.psi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class StudyProgramTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudyProgram.class);
        StudyProgram studyProgram1 = new StudyProgram();
        studyProgram1.setId(1L);
        StudyProgram studyProgram2 = new StudyProgram();
        studyProgram2.setId(studyProgram1.getId());
        assertThat(studyProgram1).isEqualTo(studyProgram2);
        studyProgram2.setId(2L);
        assertThat(studyProgram1).isNotEqualTo(studyProgram2);
        studyProgram1.setId(null);
        assertThat(studyProgram1).isNotEqualTo(studyProgram2);
    }
}
