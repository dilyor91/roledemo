package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Role2MapperTest {

    private Role2Mapper role2Mapper;

    @BeforeEach
    public void setUp() {
        role2Mapper = new Role2MapperImpl();
    }
}
