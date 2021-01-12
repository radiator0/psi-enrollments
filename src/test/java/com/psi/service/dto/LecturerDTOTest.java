package com.psi.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.psi.web.rest.TestUtil;

public class LecturerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LecturerDTO.class);
        LecturerDTO lecturerDTO1 = new LecturerDTO();
        lecturerDTO1.setId(1L);
        LecturerDTO lecturerDTO2 = new LecturerDTO();
        assertThat(lecturerDTO1).isNotEqualTo(lecturerDTO2);
        lecturerDTO2.setId(lecturerDTO1.getId());
        assertThat(lecturerDTO1).isEqualTo(lecturerDTO2);
        lecturerDTO2.setId(2L);
        assertThat(lecturerDTO1).isNotEqualTo(lecturerDTO2);
        lecturerDTO1.setId(null);
        assertThat(lecturerDTO1).isNotEqualTo(lecturerDTO2);
    }
}
