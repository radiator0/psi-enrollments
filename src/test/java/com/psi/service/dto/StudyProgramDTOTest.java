package com.psi.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class StudyProgramDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudyProgramDTO.class);
        StudyProgramDTO studyProgramDTO1 = new StudyProgramDTO();
        studyProgramDTO1.setId(1L);
        StudyProgramDTO studyProgramDTO2 = new StudyProgramDTO();
        assertThat(studyProgramDTO1).isNotEqualTo(studyProgramDTO2);
        studyProgramDTO2.setId(studyProgramDTO1.getId());
        assertThat(studyProgramDTO1).isEqualTo(studyProgramDTO2);
        studyProgramDTO2.setId(2L);
        assertThat(studyProgramDTO1).isNotEqualTo(studyProgramDTO2);
        studyProgramDTO1.setId(null);
        assertThat(studyProgramDTO1).isNotEqualTo(studyProgramDTO2);
    }
}
