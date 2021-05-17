package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.dto.SupplierDTO;
import com.hcl.capstoneserver.user.entities.AppUser;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
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

    @BeforeEach
    public void beforeEach() {
        supplierRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertNotNull(userService);
    }


    @Nested
    @DisplayName("Sign in tests")
    class SignInTest {
        @Test
        @DisplayName("It should sign in a user on correct credentials")
        public void shouldSignInOnCorrectCredentials() {
            userService.signUpClient(new Client(
                    "shel4555",
                    "sdfdsfds",
                    "Sheldon",
                    "Colombo",
                    "shel4555@gmail.com",
                    "071-2314538",
                    2.5f,
                    1234567891
            ));

            AppUser user = new AppUser();
            user.setUserId("shel4555");
            user.setPassword("sdfdsfds");

            assertNotNull(userService.signIn(user));
        }

        @Test
        @DisplayName("It should return data in required type on successful sign in")
        public void shouldReturnCorrectDataOnSignIn() {
            userService.signUpClient(new Client(
                    "shel4555",
                    "sdfdsfds",
                    "Sheldon",
                    "Colombo",
                    "shel4555@gmail.com",
                    "071-2314538",
                    2.5f,
                    1234567891
            ));

            AppUser user = new AppUser();
            user.setUserId("shel4555");
            user.setPassword("sdfdsfds");

            assertEquals("shel4555", userService.signIn(user).getUsername());
            assertEquals("CLIENT", userService.signIn(user).getUserType());
            assertNotNull(userService.signIn(user).getJwt());
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

            userService.signUpClient(new Client(
                    "shel4555",
                    "sdfdsfds",
                    "Sheldon",
                    "Colombo",
                    "shel4555@gmail.com",
                    "071-2314538",
                    2.5f,
                    1234567891
            ));

            AppUser user = new AppUser();
            user.setUserId("shel4555");
            user.setPassword("aaaa");

            Exception e = assertThrows(BadCredentialsException.class, () -> userService.signIn(user));

            assertEquals("Invalid username or password", e.getMessage());
        }
    }


    @Nested
    @DisplayName("Supplier Signup Tests")
    class SignUpSupplierTest {
        @Test
        @DisplayName("it should create a new supplier")
        public void createNewSupplier() {
            assertNotNull(userService.signUpSupplier(new Supplier(
                    "sup1",
                    "password",
                    "ma",
                    "konoha",
                    "madara@konoha.org",
                    "123456",
                    5.0F
            )));
        }

        @Test
        @DisplayName("It should generate a new supplier id")
        public void shouldGenerateSupplierId() {
            SupplierDTO supplier = userService.signUpSupplier(new Supplier(
                    "sup2",
                    "password",
                    "madara",
                    "konoha",
                    "madara1@konoha.org",
                    "123456",
                    5.0F
            ));

            assertNotNull(supplier.getSupplierId());
        }

        @Test
        @DisplayName("it should throw a UserAlreadyExistsException when username exists")
        public void shouldThrowUserAlreadyExistsException() {
            userService.signUpSupplier(new Supplier(
                    "sup1",
                    "password",
                    "ma",
                    "konoha",
                    "madara@konoha.org",
                    "123456",
                    5.0F
            ));

            assertThrows(UserAlreadyExistsException.class, () -> userService.signUpSupplier(new Supplier(
                    "sup1",
                    "password",
                    "ma",
                    "konoha",
                    "madara2@konoha.org",
                    "123456",
                    5.0F
            )));
        }

        @Test
        @DisplayName("it should throw a EmailAlreadyExistsException when username exists")
        public void shouldThrowEmailAlreadyExistsException() {
            userService.signUpSupplier(new Supplier(
                    "sup1",
                    "password",
                    "ma",
                    "konoha",
                    "madara@konoha.org",
                    "123456",
                    5.0F
            ));

            assertThrows(EmailAlreadyExistsException.class, () -> userService.signUpSupplier(new Supplier(
                    "sup2",
                    "password",
                    "ma",
                    "konoha",
                    "madara@konoha.org",
                    "123456",
                    5.0F,
                    "s001"
            )));
        }

    }


    @Nested
    @DisplayName("Client Signup Tests")
    class SignUpClientTest {
        @Test
        @DisplayName("It should create new client")
        public void shouldCreateNewClient() {
            assertEquals(
                    "Sheldon",
                    userService.signUpClient(
                            new Client(
                                    "shel2",
                                    "sdfdsfds",
                                    "Sheldon",
                                    "Colombo",
                                    "shel@gmail.com",
                                    "071-2314538",
                                    2.5f,
                                    "1001",
                                    1234567891
                            )).getName()
            );
        }

        @Test
        @DisplayName("It should generate Client Id")
        public void shouldGenerateClientId() {
            assertNotNull(userService.signUpClient(new Client(
                    "shel1",
                    "sdfdsfds",
                    "Sheldon",
                    "Colombo",
                    "shel1@gmail.com",
                    "071-2314538",
                    2.5f,
                    1234567891
            )).getClientId());
        }

        @Test
        @DisplayName("it should throw a UserAlreadyExistsException when username exists")
        public void shouldThrowUserAlreadyExistsException() {
            userService.signUpClient(new Client(
                    "shel1",
                    "sdfdsfds",
                    "Sheldon",
                    "Colombo",
                    "shel1@gmail.com",
                    "071-2314538",
                    2.5f,
                    1234567891
            ));

            assertThrows(UserAlreadyExistsException.class, () -> userService.signUpClient(new Client(
                    "shel1",
                    "sdfdsfds",
                    "Sheldon",
                    "Colombo",
                    "shel11@gmail.com",
                    "071-2314538",
                    2.5f,
                    1234567891
            )));
        }

        @Test
        @DisplayName("it should throw a EmailAlreadyExistsException when username exists")
        public void shouldThrowEmailAlreadyExistsException() {
            userService.signUpClient(new Client(
                    "shel1",
                    "sdfdsfds",
                    "Sheldon",
                    "Colombo",
                    "shel1@gmail.com",
                    "071-2314538",
                    2.5f,
                    1234567891
            ));

            assertThrows(EmailAlreadyExistsException.class, () -> userService.signUpClient(new Client(
                    "shel11",
                    "sdfdsfds",
                    "Sheldon",
                    "Colombo",
                    "shel1@gmail.com",
                    "071-2314538",
                    2.5f,
                    1234567891
            )));
        }

    }


}
