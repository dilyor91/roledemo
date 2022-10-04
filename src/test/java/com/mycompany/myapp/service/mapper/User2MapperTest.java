package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class User2MapperTest {

    private User2Mapper user2Mapper;

    @BeforeEach
    public void setUp() {
        user2Mapper = new User2MapperImpl();
    }
}
