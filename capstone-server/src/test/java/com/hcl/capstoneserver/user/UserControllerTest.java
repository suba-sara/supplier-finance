package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.dto.AppUserWithPasswordDTO;
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

    // Supplier Testings
    @Test
    @DisplayName("it should create a supplier on correct parameters")
    public void supplierCorrectSignup() {
        PersonWithPasswordDTO dto = new PersonWithPasswordDTO();
        dto.setUserId("sup1");
        dto.setPassword("password");
        dto.setName("madara");
        dto.setAddress("address");
        dto.setEmail("madara1@konoh.org");
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
    @DisplayName("It should generate supplier id")
    public void shouldGenerateSupplierId() {
        PersonWithPasswordDTO dto = new PersonWithPasswordDTO();
        dto.setUserId("sup2");
        dto.setPassword("password");
        dto.setName("madara");
        dto.setAddress("address");
        dto.setEmail("madara2@konoh.org");
        dto.setPhone("123456");
        dto.setInterestRate(5.0F);

        webTestClient.post()
                     .uri(String.format("http://localhost:%d/api/sign-up/supplier", port))
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(Mono.just(dto), PersonWithPasswordDTO.class)
                     .exchange()
                     .expectStatus()
                     .is2xxSuccessful()
                     .expectBody()
                     .jsonPath("$.supplierId").isNotEmpty();
    }

    @Test
    @DisplayName("It should throw error when supplier has exists userId")
    public void shouldCheckSupplierUserIdExisted() {
        PersonWithPasswordDTO dto = new PersonWithPasswordDTO();
        dto.setUserId("sup3");
        dto.setPassword("password");
        dto.setName("madara");
        dto.setAddress("address");
        dto.setEmail("madara3@konoh.org");
        dto.setPhone("123456");
        dto.setInterestRate(5.0F);

        // create user
        webTestClient.post()
                     .uri(String.format("http://localhost:%d/api/sign-up/supplier", port))
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(Mono.just(dto), PersonWithPasswordDTO.class)
                     .exchange()
                     .expectStatus()
                     .is2xxSuccessful()
                     .expectBody()
                     .jsonPath("$.userId").isEqualTo(dto.getUserId());

        // test the error
        webTestClient.post()
                     .uri(String.format("http://localhost:%d/api/sign-up/supplier", port))
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(Mono.just(dto), PersonWithPasswordDTO.class)
                     .exchange()
                     .expectStatus()
                     .isBadRequest()
                     .expectBody()
                     .jsonPath("$.errors[0].message")
                     .isEqualTo(String.format("User with username %s already exits", dto.getUserId()));
    }

    @Test
    @DisplayName("it should throw errors on supplier not providing a userid")
    public void supplierInvalidSignupUserId() {
        PersonWithPasswordDTO dto = new PersonWithPasswordDTO();
        dto.setPassword("password");
        dto.setName("madara");
        dto.setAddress("address");
        dto.setEmail("madara4@konoh.org");
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

    @Test
    @DisplayName("It should throw an exception when supplier has exists email")
    public void shouldCheckSupplierEmailExisted() {
        PersonWithPasswordDTO dto = new PersonWithPasswordDTO();
        dto.setUserId("sup5");
        dto.setPassword("password");
        dto.setName("madara");
        dto.setAddress("address");
        dto.setEmail("madara5@konoh.org");
        dto.setPhone("123456");
        dto.setInterestRate(5.0F);

        // create user
        webTestClient.post()
                     .uri(String.format("http://localhost:%d/api/sign-up/supplier", port))
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(Mono.just(dto), PersonWithPasswordDTO.class)
                     .exchange()
                     .expectStatus()
                     .is2xxSuccessful()
                     .expectBody()
                     .jsonPath("$.userId").isEqualTo(dto.getUserId());

        //test the error
        dto = new PersonWithPasswordDTO();
        dto.setUserId("sup6");
        dto.setPassword("password");
        dto.setName("madara");
        dto.setAddress("address");
        dto.setEmail("madara5@konoh.org");
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
                     .jsonPath("$.errors[0].message")
                     .isEqualTo(String.format("User with email %s already exits", dto.getEmail()));
    }

    // Client Testings
    @Test
    @DisplayName("it should be create a new Client")
    public void shouldCreateClient() {
        PersonWithPasswordDTO person = new PersonWithPasswordDTO();
        person.setUserId("Tester1");
        person.setPassword("sdsdfsdfs");
        person.setName("Sheldon");
        person.setAddress("colombo");
        person.setEmail("shedfds1@gmail.com");
        person.setPhone("21312");
        person.setInterestRate(2.0F);

        webTestClient.post()
                     .uri(String.format("http://localhost:%d/api/sign-up/client", port))
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(Mono.just(person), PersonWithPasswordDTO.class)
                     .exchange()
                     .expectStatus()
                     .is2xxSuccessful()
                     .expectBody()
                     .jsonPath("$.name").isEqualTo("Sheldon");
    }

    @Test
    @DisplayName("It should generate client id")
    public void shouldGenerateClientId() {
        PersonWithPasswordDTO person = new PersonWithPasswordDTO();
        person.setUserId("Tester2");
        person.setPassword("sdsdfsdfs");
        person.setName("Sheldon");
        person.setAddress("colombo");
        person.setEmail("shedfds2@gmail.com");
        person.setPhone("21312");
        person.setInterestRate(2.0F);

        webTestClient.post()
                     .uri(String.format("http://localhost:%d/api/sign-up/client", port))
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(Mono.just(person), PersonWithPasswordDTO.class)
                     .exchange()
                     .expectStatus()
                     .is2xxSuccessful()
                     .expectBody()
                     .jsonPath("$.clientId").isNotEmpty();
    }

    @Test
    @DisplayName("It should throw error when client has exists userId")
    public void shouldCheckClientUserIdExisted() {
        PersonWithPasswordDTO person = new PersonWithPasswordDTO();
        person.setUserId("Tester3");
        person.setPassword("sdsdfsdfs");
        person.setName("Sheldon");
        person.setAddress("colombo");
        person.setEmail("shedfds3@gmail.com");
        person.setPhone("21312");
        person.setInterestRate(2.0F);

        // create user
        webTestClient.post()
                     .uri(String.format("http://localhost:%d/api/sign-up/client", port))
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(Mono.just(person), PersonWithPasswordDTO.class)
                     .exchange()
                     .expectStatus()
                     .is2xxSuccessful()
                     .expectBody()
                     .jsonPath("$.userId").isEqualTo(person.getUserId());

        // test the error
        webTestClient.post()
                     .uri(String.format("http://localhost:%d/api/sign-up/client", port))
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(Mono.just(person), PersonWithPasswordDTO.class)
                     .exchange()
                     .expectStatus()
                     .isBadRequest()
                     .expectBody()
                     .jsonPath("$.errors[0].message")
                     .isEqualTo(String.format("User with username %s already exits", person.getUserId()));
    }

    @Test
    @DisplayName("it should throw errors on client not providing a userid")
    public void clientInvalidSignupUserId() {
        PersonWithPasswordDTO person = new PersonWithPasswordDTO();
        person.setPassword("sdsdfsdfs");
        person.setName("Sheldon");
        person.setAddress("colombo");
        person.setEmail("shedfds4@gmail.com");
        person.setPhone("21312");
        person.setInterestRate(2.0F);


        webTestClient.post()
                     .uri(String.format("http://localhost:%d/api/sign-up/client", port))
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(Mono.just(person), PersonWithPasswordDTO.class)
                     .exchange()
                     .expectStatus()
                     .isBadRequest()
                     .expectBody()
                     .jsonPath("$.errors[0].field").isEqualTo("userId");
    }

    @Test
    @DisplayName("It should throw an exception when client has exists email")
    public void shouldCheckClientEmailExisted() {
        PersonWithPasswordDTO person = new PersonWithPasswordDTO();
        person.setUserId("Tester5");
        person.setPassword("sdsdfsdfs");
        person.setName("Sheldon");
        person.setAddress("colombo");
        person.setEmail("shedfds5@gmail.com");
        person.setPhone("21312");
        person.setInterestRate(2.0F);

        // create user
        webTestClient.post()
                     .uri(String.format("http://localhost:%d/api/sign-up/client", port))
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(Mono.just(person), PersonWithPasswordDTO.class)
                     .exchange()
                     .expectStatus()
                     .is2xxSuccessful()
                     .expectBody()
                     .jsonPath("$.userId").isEqualTo(person.getUserId());

        //test the error
        person = new PersonWithPasswordDTO();
        person.setUserId("Tester6");
        person.setPassword("sdsdfsdfs");
        person.setName("Sheldon");
        person.setAddress("colombo");
        person.setEmail("shedfds5@gmail.com");
        person.setPhone("21312");
        person.setInterestRate(2.0F);

        webTestClient.post()
                     .uri(String.format("http://localhost:%d/api/sign-up/client", port))
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(Mono.just(person), PersonWithPasswordDTO.class)
                     .exchange()
                     .expectStatus()
                     .isBadRequest()
                     .expectBody()
                     .jsonPath("$.errors[0].message")
                     .isEqualTo(String.format("User with email %s already exits", person.getEmail()));
    }

    @Test
    @DisplayName("It should give appropriate error on bad credentials")
    public void handleBadCredentialsException() {
        AppUserWithPasswordDTO user = new AppUserWithPasswordDTO("aaa", "bbb");

        webTestClient.post()
                     .uri(String.format("http://localhost:%d/api/sign-in", port))
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(Mono.just(user), AppUserWithPasswordDTO.class)
                     .exchange()
                     .expectStatus()
                     .isForbidden()
                     .expectBody()
                     .jsonPath("$")
                     .isEqualTo("Invalid username or password");
    }
}