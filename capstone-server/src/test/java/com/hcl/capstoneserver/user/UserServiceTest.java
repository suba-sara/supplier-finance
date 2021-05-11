package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.dto.SupplierDTO;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(userService);
    }

    @Test
    @DisplayName("it should create a new supplier")
    public void createNewSupplier() {
        Assertions.assertNotNull(userService.signUpSupplier(new Supplier(
                "sup1",
                "password",
                "ma",
                "konoha",
                "madara@konoha.org",
                "123456",
                5.0F,
                "s001"
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
                "madara@konoha.org",
                "123456",
                5.0F,
                "s002"
        ));

        Assertions.assertNotNull(supplier.getSupplierId());
    }

    @Test
    @DisplayName("It should create new client")
    public void shouldCreateNewClient() {
        Assertions.assertEquals(
                "Sheldon",
                userService.signUpClient(
                        new Client(
                                "shel",
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
        Assertions.assertNotNull(userService.signUpClient(new Client(
                "shel",
                "sdfdsfds",
                "Sheldon",
                "Colombo",
                "shel@gmail.com",
                "071-2314538",
                2.5f,
                1234567891
        )).getClientId());
    }
}
