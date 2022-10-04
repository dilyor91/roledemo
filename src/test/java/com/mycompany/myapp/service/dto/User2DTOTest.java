package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class User2DTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(User2DTO.class);
        User2DTO user2DTO1 = new User2DTO();
        user2DTO1.setId(1L);
        User2DTO user2DTO2 = new User2DTO();
        assertThat(user2DTO1).isNotEqualTo(user2DTO2);
        user2DTO2.setId(user2DTO1.getId());
        assertThat(user2DTO1).isEqualTo(user2DTO2);
        user2DTO2.setId(2L);
        assertThat(user2DTO1).isNotEqualTo(user2DTO2);
        user2DTO1.setId(null);
        assertThat(user2DTO1).isNotEqualTo(user2DTO2);
    }
}
