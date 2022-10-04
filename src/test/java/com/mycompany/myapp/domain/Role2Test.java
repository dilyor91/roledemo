package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Role2Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Role2.class);
        Role2 role21 = new Role2();
        role21.setId(1L);
        Role2 role22 = new Role2();
        role22.setId(role21.getId());
        assertThat(role21).isEqualTo(role22);
        role22.setId(2L);
        assertThat(role21).isNotEqualTo(role22);
        role21.setId(null);
        assertThat(role21).isNotEqualTo(role22);
    }
}
