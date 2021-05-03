package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.dto.PersonWithPasswordDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserControllerTest {
    @Autowired
    private UserService userService;

    @Autowired
    UserController userController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(userController);
    }

    @Test
    @DisplayName("it should call the signUpClientService on correct parameters")
    public void supplierSignup() {
        PersonWithPasswordDTO person = new PersonWithPasswordDTO(
                "sup1",
                "p",
                "ma",
                "konoha",
                "madara@konoha.org",
                "123456",
                5.0F
        );
    }
}
