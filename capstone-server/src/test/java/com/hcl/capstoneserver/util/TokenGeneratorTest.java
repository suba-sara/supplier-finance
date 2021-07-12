package com.hcl.capstoneserver.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenGeneratorTest {
    @Autowired
    TokenGenerator tokenGenerator;

    @Test
    void shouldGenerateToken() {
        Assertions.assertNotNull(tokenGenerator.generateToken());
    }
}
