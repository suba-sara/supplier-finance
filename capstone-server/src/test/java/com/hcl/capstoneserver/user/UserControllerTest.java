package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.dto.PersonWithPasswordDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    UserController userController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(userController);
    }

    @Test
    @DisplayName("it should create a supplier on correct parameters")
    public void supplierCorrectSignup() {
        PersonWithPasswordDTO dto = new PersonWithPasswordDTO();
        dto.setUserId("sup1");
        dto.setPassword("password");
        dto.setName("madara");
        dto.setAddress("address");
        dto.setEmail("madara@konoh.org");
        dto.setPhone("123456");
        dto.setInterestRate(5.0F);


        webTestClient.post()
                .uri(String.format("http://localhost:%d/api/sign-up/supplier", port))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), PersonWithPasswordDTO.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.userId").isEqualTo("sup1");
    }

    @Test
    @DisplayName("it should throw errors on invalid userid")
    public void supplierInvalidSignupUserId() {
        PersonWithPasswordDTO dto = new PersonWithPasswordDTO();
        dto.setPassword("password");
        dto.setName("madara");
        dto.setAddress("address");
        dto.setEmail("madara@konoh.org");
        dto.setPhone("123456");
        dto.setInterestRate(5.0F);


        webTestClient.post()
                .uri(String.format("http://localhost:%d/api/sign-up/supplier", port))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), PersonWithPasswordDTO.class)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .jsonPath("$.errors[0].field").isEqualTo("userId");
    }
}
