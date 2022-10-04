package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Privilege2MapperTest {

    private Privilege2Mapper privilege2Mapper;

    @BeforeEach
    public void setUp() {
        privilege2Mapper = new Privilege2MapperImpl();
    }
}
