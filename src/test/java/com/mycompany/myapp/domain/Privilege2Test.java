package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Privilege2Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Privilege2.class);
        Privilege2 privilege21 = new Privilege2();
        privilege21.setId(1L);
        Privilege2 privilege22 = new Privilege2();
        privilege22.setId(privilege21.getId());
        assertThat(privilege21).isEqualTo(privilege22);
        privilege22.setId(2L);
        assertThat(privilege21).isNotEqualTo(privilege22);
        privilege21.setId(null);
        assertThat(privilege21).isNotEqualTo(privilege22);
    }
}
