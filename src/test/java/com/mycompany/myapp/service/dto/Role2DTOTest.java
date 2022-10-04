package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Role2DTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Role2DTO.class);
        Role2DTO role2DTO1 = new Role2DTO();
        role2DTO1.setId(1L);
        Role2DTO role2DTO2 = new Role2DTO();
        assertThat(role2DTO1).isNotEqualTo(role2DTO2);
        role2DTO2.setId(role2DTO1.getId());
        assertThat(role2DTO1).isEqualTo(role2DTO2);
        role2DTO2.setId(2L);
        assertThat(role2DTO1).isNotEqualTo(role2DTO2);
        role2DTO1.setId(null);
        assertThat(role2DTO1).isNotEqualTo(role2DTO2);
    }
}
