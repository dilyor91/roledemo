package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class User2Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(User2.class);
        User2 user21 = new User2();
        user21.setId(1L);
        User2 user22 = new User2();
        user22.setId(user21.getId());
        assertThat(user21).isEqualTo(user22);
        user22.setId(2L);
        assertThat(user21).isNotEqualTo(user22);
        user21.setId(null);
        assertThat(user21).isNotEqualTo(user22);
    }
}
