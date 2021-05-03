package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.dto.SignUpSupplierRequestDTO;
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
    @DisplayName("it should create a new supplier")
    public void createNewSupplier() {
        Assertions.assertNotNull(userService.signUpSupplier(new SignUpSupplierRequestDTO(
                "sup1",
                "password",
                "madara",
                "konoha",
                "madara@konoha.org",
                "123456",
                5.0F,
                10002
        )));
    }

    @Test
    @DisplayName("It should generate a new supplier id")
    public void shouldGenerateSupplierId() {
        Supplier supplier = userService.signUpSupplier(new SignUpSupplierRequestDTO(
                "sup2",
                "password",
                "madara",
                "konoha",
                "madara@konoha.org",
                "123456",
                5.0F,
                10002
        ));

        Assertions.assertNotNull(supplier.getSupplierId());
    }
}
