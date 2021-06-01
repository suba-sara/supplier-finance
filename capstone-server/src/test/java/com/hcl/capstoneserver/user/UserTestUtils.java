package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.entities.AppUser;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTestUtils {
    @Autowired
    UserService userService;

    public void createAUser(UserType type) {
        switch (type) {
            case SUPPLIER:
                userService.signUpSupplier(new Supplier(
                        "supplier",
                        "password",
                        "supplier",
                        "Colombo",
                        "supplier@gmail.com",
                        "071-2314531a",
                        2.5f
                ));
                break;
            case CLIENT:
                userService.signUpClient(new Client(
                        "client",
                        "password",
                        "client",
                        "Colombo",
                        "client@gmail.com",
                        "071-2314538",
                        2.5f,
                        1234567891
                ));
        }

    }

    public String loginAUser(UserType userType) {
        String token = "Bearer ";
        switch (userType) {
            case CLIENT:
                token += userService.signIn(new AppUser("client", "password", UserType.CLIENT)).getJwt();
                break;
            case SUPPLIER:
                token += userService.signIn(new AppUser("supplier", "password", UserType.SUPPLIER)).getJwt();
        }
        return token;
    }
}
