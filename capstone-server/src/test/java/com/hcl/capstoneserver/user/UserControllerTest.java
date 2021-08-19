package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.dto.*;
import com.hcl.capstoneserver.user.repositories.ClientRepository;
import com.hcl.capstoneserver.user.repositories.SupplierRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    UserController userController;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UserTestUtils userTestUtils;

    @Autowired
    InvoiceRepository invoiceRepository;

    List<SupplierDTO> suppliers;
    List<ClientDTO> clients;

    @BeforeEach
    public void beforeEach() {
        invoiceRepository.deleteAll();
        supplierRepository.deleteAll();
        clientRepository.deleteAll();
        userTestUtils.accountCreate();
        suppliers = userTestUtils.createASupplier();
        clients = userTestUtils.createAClient();
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(userController);
    }

    @Nested
    @DisplayName("Sign in tests")
    class SignInTests {
        @Test
        @DisplayName("It should sign in a user on correct credentials")
        public void shouldSignInOnCorrectCredentials() {
            AppUserWithPasswordDTO user = new AppUserWithPasswordDTO("client", "password");

            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/sign-in", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(Mono.just(user), AppUserWithPasswordDTO.class)
                         .exchange()
                         .expectStatus()
                         .is2xxSuccessful();
        }

        @Test
        @DisplayName("It should return required response on successful sign in")
        public void shouldReturnRequiredResponseOnSuccess() {
            AppUserWithPasswordDTO user = new AppUserWithPasswordDTO("client", "password");

            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/sign-in", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(Mono.just(user), AppUserWithPasswordDTO.class)
                         .exchange()
                         .expectStatus()
                         .is2xxSuccessful()
                         .expectBody()
                         .jsonPath("$.username")
                         .isEqualTo("client")
                         .jsonPath("$.userType")
                         .isEqualTo("CLIENT")
                         .jsonPath("$.jwt")
                         .isNotEmpty();

        }


        @Test
        @DisplayName("It should give appropriate error on incorrect username")
        public void shouldGiveErrorOnIncorrectUsername() {
            AppUserWithPasswordDTO user = new AppUserWithPasswordDTO("aaa", "bbb");

            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/sign-in", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(Mono.just(user), AppUserWithPasswordDTO.class)
                         .exchange()
                         .expectStatus()
                         .isUnauthorized()
                         .expectBody()
                         .jsonPath("$.message")
                         .isEqualTo("Invalid username or password");
        }

        @Test
        @DisplayName("It should give appropriate error on incorrect password")
        public void shouldGiveErrorOnIncorrectPassword() {
            AppUserWithPasswordDTO user = new AppUserWithPasswordDTO("client", "bbb");

            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/sign-in", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(Mono.just(user), AppUserWithPasswordDTO.class)
                         .exchange()
                         .expectStatus()
                         .isUnauthorized()
                         .expectBody()
                         .jsonPath("$.message")
                         .isEqualTo("Invalid username or password");
        }
    }

    @Nested
    @DisplayName("refresh token tests")
    class RefreshTokenTests {
        @Test
        @DisplayName("It should return the correct response on success")
        public void refreshTokenSuccess() {
            // get jwt token for the user
            AppUserWithPasswordDTO user = new AppUserWithPasswordDTO("client", "password");
            ResponseEntity<JwtWithTypeDTO> response = userController.signIn(user);

            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/refresh-token", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .header("Authorization", "Bearer " + Objects.requireNonNull(response.getBody()).getJwt())
                         .exchange()
                         .expectStatus()
                         .is2xxSuccessful()
                         .expectBody()
                         .jsonPath("$.username")
                         .isEqualTo("client")
                         .jsonPath("$.userType")
                         .isEqualTo("CLIENT")
                         .jsonPath("$.jwt")
                         .isNotEmpty();
        }

        @Test
        @DisplayName("It should return appropriate error when jwt not provided")
        public void refreshTokenUnAothorized() {
            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/refresh-token", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .exchange()
                         .expectStatus()
                         .isUnauthorized();
        }
    }

    @Nested
    @DisplayName("Supplier Signup Tests")
    class SignUpSupplierTests {
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
            dto.setAccountNumber("10000239");
            dto.setOtp("482410");


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
            dto.setAccountNumber("10000239");
            dto.setOtp("482410");

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
        @DisplayName("it should throw an error when userid is not provided")
        public void supplierInvalidSignupUserId() {
            PersonWithPasswordDTO dto = new PersonWithPasswordDTO();
            dto.setPassword("password");
            dto.setName("madara");
            dto.setAddress("address");
            dto.setEmail("madara4@konoh.org");
            dto.setPhone("123456");
            dto.setAccountNumber("10000239");
            dto.setOtp("482410");


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
        @DisplayName("It should throw an error when userId already exists")
        public void shouldCheckSupplierUserIdExisted() {
            PersonWithPasswordDTO dto = new PersonWithPasswordDTO();
            dto.setUserId("supplier");
            dto.setPassword("password");
            dto.setName("madara");
            dto.setAddress("address");
            dto.setEmail("madara3@konoh.org");
            dto.setPhone("123456");
            dto.setAccountNumber("10000239");
            dto.setOtp("482410");

            // test the error
            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/sign-up/supplier", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(Mono.just(dto), PersonWithPasswordDTO.class)
                         .exchange()
                         .expectStatus()
                         .isBadRequest()
                         .expectBody()
                         .jsonPath("$.message")
                         .isEqualTo(String.format("User with username %s already exits.", dto.getUserId()));
        }


        @Test
        @DisplayName("It should throw an error when email already exists")
        public void shouldCheckSupplierEmailExisted() {
            PersonWithPasswordDTO dto = new PersonWithPasswordDTO();
            dto.setUserId("sup6");
            dto.setPassword("password");
            dto.setName("madara");
            dto.setAddress("address");
            dto.setEmail("supplier@gmail.com");
            dto.setPhone("123456");
            dto.setAccountNumber("10000239");
            dto.setOtp("482410");

            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/sign-up/supplier", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(Mono.just(dto), PersonWithPasswordDTO.class)
                         .exchange()
                         .expectStatus()
                         .isBadRequest()
                         .expectBody()
                         .jsonPath("$.message")
                         .isEqualTo(String.format("User with email %s already exits.", dto.getEmail()));
        }
    }

    @Nested
    @DisplayName("Client Signup Tests")
    class SignUpClientTests {
        @Test
        @DisplayName("it should create a client on correct parameters")
        public void clientCorrectSignup() {
            PersonWithPasswordDTO dto = new PersonWithPasswordDTO();
            dto.setUserId("Tester1");
            dto.setPassword("sdsdfsdfs");
            dto.setName("Sheldon");
            dto.setAddress("colombo");
            dto.setEmail("shedfds1@gmail.com");
            dto.setPhone("21312");
            dto.setAccountNumber("10000239");
            dto.setOtp("482410");

            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/sign-up/client", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(Mono.just(dto), PersonWithPasswordDTO.class)
                         .exchange()
                         .expectStatus()
                         .isCreated()
                         .expectBody()
                         .jsonPath("$.userId").isEqualTo("Tester1");
        }

        @Test
        @DisplayName("It should generate client id")
        public void shouldGenerateClientId() {
            PersonWithPasswordDTO dto = new PersonWithPasswordDTO();
            dto.setUserId("Tester2");
            dto.setPassword("sdsdfsdfs");
            dto.setName("Sheldon");
            dto.setAddress("colombo");
            dto.setEmail("shedfds2@gmail.com");
            dto.setPhone("21312");
            dto.setAccountNumber("10000239");
            dto.setOtp("482410");

            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/sign-up/client", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(Mono.just(dto), PersonWithPasswordDTO.class)
                         .exchange()
                         .expectStatus()
                         .is2xxSuccessful()
                         .expectBody()
                         .jsonPath("$.clientId").isNotEmpty();
        }

        @Test
        @DisplayName("it should throw an error when userid is not provided")
        public void clientInvalidSignupUserId() {
            PersonWithPasswordDTO dto = new PersonWithPasswordDTO();
            dto.setPassword("sdsdfsdfs");
            dto.setName("Sheldon");
            dto.setAddress("colombo");
            dto.setEmail("shedfds4@gmail.com");
            dto.setPhone("21312");
            dto.setAccountNumber("10000239");
            dto.setOtp("482410");

            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/sign-up/client", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(Mono.just(dto), PersonWithPasswordDTO.class)
                         .exchange()
                         .expectStatus()
                         .isBadRequest()
                         .expectBody()
                         .jsonPath("$.message").isEqualTo("Validation Failed");
        }

        @Test
        @DisplayName("It should throw an error when userId already exists")
        public void shouldCheckClientUserIdExisted() {
            PersonWithPasswordDTO dto = new PersonWithPasswordDTO();
            dto.setUserId("client");
            dto.setPassword("sdsdfsdfs");
            dto.setName("Sheldon");
            dto.setAddress("colombo");
            dto.setEmail("shedfds3@gmail.com");
            dto.setPhone("21312");
            dto.setAccountNumber("10000239");
            dto.setOtp("482410");

            // test the error
            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/sign-up/client", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(Mono.just(dto), PersonWithPasswordDTO.class)
                         .exchange()
                         .expectStatus()
                         .isBadRequest()
                         .expectBody()
                         .jsonPath("$.message")
                         .isEqualTo(String.format("User with username %s already exits.", dto.getUserId()));
        }

        @Test
        @DisplayName("It should throw an error when email already exists")
        public void shouldCheckClientEmailExisted() {
            PersonWithPasswordDTO dto = new PersonWithPasswordDTO();
            dto.setUserId("Tester5");
            dto.setPassword("sdsdfsdfs");
            dto.setName("Sheldon");
            dto.setAddress("colombo");
            dto.setEmail("client@gmail.com");
            dto.setPhone("21312");
            dto.setAccountNumber("10000239");
            dto.setOtp("482410");

            // test the error
            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/sign-up/client", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(Mono.just(dto), PersonWithPasswordDTO.class)
                         .exchange()
                         .expectStatus()
                         .isBadRequest()
                         .expectBody()
                         .jsonPath("$.message")
                         .isEqualTo(String.format("User with email %s already exits.", dto.getEmail()));
        }
    }
}