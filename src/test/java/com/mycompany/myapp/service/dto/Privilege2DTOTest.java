package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Privilege2DTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Privilege2DTO.class);
        Privilege2DTO privilege2DTO1 = new Privilege2DTO();
        privilege2DTO1.setId(1L);
        Privilege2DTO privilege2DTO2 = new Privilege2DTO();
        assertThat(privilege2DTO1).isNotEqualTo(privilege2DTO2);
        privilege2DTO2.setId(privilege2DTO1.getId());
        assertThat(privilege2DTO1).isEqualTo(privilege2DTO2);
        privilege2DTO2.setId(2L);
        assertThat(privilege2DTO1).isNotEqualTo(privilege2DTO2);
        privilege2DTO1.setId(null);
        assertThat(privilege2DTO1).isNotEqualTo(privilege2DTO2);
    }
}
