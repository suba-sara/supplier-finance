package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.dto.JwtWithTypeDTO;
import com.hcl.capstoneserver.user.dto.PersonWithPasswordDTO;
import com.hcl.capstoneserver.user.dto.SupplierDTO;
import com.hcl.capstoneserver.user.entities.AppUser;
import com.hcl.capstoneserver.user.exceptions.EmailAlreadyExistsException;
import com.hcl.capstoneserver.user.exceptions.UserAlreadyExistsException;
import com.hcl.capstoneserver.user.repositories.ClientRepository;
import com.hcl.capstoneserver.user.repositories.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

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
        userTestUtils.accountCreate();
        userTestUtils.createAClient();
    }

    @Test
    void contextLoads() {
        assertNotNull(userService);
    }

    @Nested
    @DisplayName("Sign in tests")
    class SignInTests {
        @Test
        @DisplayName("It should sign in a user on correct credentials")
        public void shouldSignInOnCorrectCredentials() {
            AppUser user = new AppUser();
            user.setUserId("client");
            user.setPassword("password");

            assertNotNull(userService.signIn(user));
        }

        @Test
        @DisplayName("It should return data in required type on successful sign in")
        public void shouldReturnCorrectDataOnSignIn() {
            AppUser user = new AppUser();
            user.setUserId("client");
            user.setPassword("password");

            JwtWithTypeDTO response = userService.signIn(user);

            assertEquals("client", response.getUsername());
            assertEquals("CLIENT", response.getUserType());
            assertNotNull(response.getJwt());
        }

        @Test
        @DisplayName("It should throw BadCredentialsException when username is not found")
        public void shouldThrowExceptionOnInvalidUsername() {

            AppUser user = new AppUser();
            user.setUserId("aadoesnotexists");
            user.setPassword("aaaa");

            Exception e = assertThrows(BadCredentialsException.class, () -> userService.signIn(user));

            assertEquals("Invalid username or password", e.getMessage());
        }

        @Test
        @DisplayName("It should throw BadCredentialsException when password is invalid")
        public void shouldHandleInvalidCredentialsError() {

            AppUser user = new AppUser();
            user.setUserId("client");
            user.setPassword("aaaa");

            Exception e = assertThrows(BadCredentialsException.class, () -> userService.signIn(user));

            assertEquals("Invalid username or password", e.getMessage());
        }
    }

    @Nested
    @DisplayName("refresh token tests")
    class RefreshTokenTests {
        @Test
        @DisplayName("it should return a valid response")
        public void shouldReturnValidResponse() {


            JwtWithTypeDTO response = userService.refreshToken("client");
            assertEquals("client", response.getUsername());
            assertEquals("CLIENT", response.getUserType());
            assertNotNull(response.getJwt());

        }
    }

    @Nested
    @DisplayName("Supplier Signup Tests")
    class SignUpSupplierTests {
        @Test
        @DisplayName("it should create a new supplier")
        public void createNewSupplier() {
            assertNotNull(userService.signUpSupplier(
                    new PersonWithPasswordDTO(
                            "sup1",
                            "ma",
                            "Colombo",
                            "madara@konoha.org",
                            "071-2314532",
                            "10000239",
                            "password",
                            "482410"
                    )));
        }

        @Test
        @DisplayName("It should generate a new supplier id")
        public void shouldGenerateSupplierId() {
            SupplierDTO supplier = userService.signUpSupplier(new PersonWithPasswordDTO(
                    "sup2",
                    "ma",
                    "Colombo",
                    "madara2@konoha.org",
                    "071-2364552",
                    "10000239",
                    "password",
                    "482410"
            ));

            assertNotNull(supplier.getSupplierId());
        }

        @Test
        @DisplayName("it should throw a UserAlreadyExistsException when username exists")
        public void shouldThrowUserAlreadyExistsException() {
            userTestUtils.createASupplier();
            assertThrows(UserAlreadyExistsException.class, () -> userService.signUpSupplier(new PersonWithPasswordDTO(
                    "supplier",
                    "ma",
                    "Colombo",
                    "madara21@konoha.org",
                    "071-2324552",
                    "10000239",
                    "password",
                    "482410"
            )));
        }

        @Test
        @DisplayName("it should throw a EmailAlreadyExistsException when username exists")
        public void shouldThrowEmailAlreadyExistsException() {
            userTestUtils.createASupplier();
            assertThrows(EmailAlreadyExistsException.class, () -> userService.signUpSupplier(new PersonWithPasswordDTO(
                    "supplier23",
                    "ma",
                    "Colombo",
                    "supplier@gmail.com",
                    "071-2314852",
                    "10000239",
                    "password",
                    "482410"
            )));
        }
    }

    @Nested
    @DisplayName("Client Signup Tests")
    class SignUpClientTests {
        @Test
        @DisplayName("It should create new client")
        public void shouldCreateNewClient() {
            assertEquals(
                    "Sheldon",
                    userService.signUpClient(
                            new PersonWithPasswordDTO(
                                    "shel2",
                                    "Sheldon",
                                    "Colombo",
                                    "shel@gmail.com",
                                    "071-2314538",
                                    "10000239",
                                    "sdfdsfds",
                                    "482410"
                            )).getName()
            );
        }

        @Test
        @DisplayName("It should generate Client Id")
        public void shouldGenerateClientId() {
            assertNotNull(userService.signUpClient(
                    new PersonWithPasswordDTO(
                            "shel1",
                            "Sheldon",
                            "Colombo",
                            "shel1@gmail.com",
                            "071-2314538",
                            "10000239",
                            "sdfdsfds",
                            "482410"
                    )).getClientId());
        }

        @Test
        @DisplayName("it should throw a UserAlreadyExistsException when username exists")
        public void shouldThrowUserAlreadyExistsException() {
            assertThrows(UserAlreadyExistsException.class, () -> userService.signUpClient(
                    new PersonWithPasswordDTO(
                            "client",
                            "Sheldon",
                            "Colombo",
                            "shel1a@gmail.com",
                            "071-2314538",
                            "10000239",
                            "sdfdsfds",
                            "482410"
                    )));
        }

        @Test
        @DisplayName("it should throw a EmailAlreadyExistsException when username exists")
        public void shouldThrowEmailAlreadyExistsException() {

            assertThrows(EmailAlreadyExistsException.class, () -> userService.signUpClient(
                    new PersonWithPasswordDTO(
                            "shel11",
                            "Sheldon",
                            "Colombo",
                            "client@gmail.com",
                            "071-2314538",
                            "10000239",
                            "sdfdsfds",
                            "482410"
                    )));
        }

    }
}