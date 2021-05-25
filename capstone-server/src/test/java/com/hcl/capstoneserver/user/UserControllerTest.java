package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.dto.AppUserWithPasswordDTO;
import com.hcl.capstoneserver.user.dto.JwtWithTypeDTO;
import com.hcl.capstoneserver.user.dto.PersonWithPasswordDTO;
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

    @BeforeEach
    public void beforeEach() {
        invoiceRepository.deleteAll();
        supplierRepository.deleteAll();
        clientRepository.deleteAll();
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
            userTestUtils.createAUser(UserType.CLIENT);

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
            userTestUtils.createAUser(UserType.CLIENT);

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
            userTestUtils.createAUser(UserType.CLIENT);
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
            userTestUtils.createAUser(UserType.CLIENT);

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
            userTestUtils.createAUser(UserType.CLIENT);

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
        @DisplayName("it should throw an error when userid is not provided")
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
        @DisplayName("It should throw an error when userId already exists")
        public void shouldCheckSupplierUserIdExisted() {
            userTestUtils.createAUser(UserType.SUPPLIER);

            PersonWithPasswordDTO dto = new PersonWithPasswordDTO();
            dto.setUserId("supplier");
            dto.setPassword("password");
            dto.setName("madara");
            dto.setAddress("address");
            dto.setEmail("madara3@konoh.org");
            dto.setPhone("123456");
            dto.setInterestRate(5.0F);

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
        @DisplayName("It should throw an error when email already exists")
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
    }

    @Nested
    @DisplayName("Client Signup Tests")
    class SignUpClientTests {

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
        @DisplayName("it should throw an error when userid is not provided")
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
        @DisplayName("It should throw an error when userId already exists")
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
        @DisplayName("It should throw an error when email already exists")
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


    }

}