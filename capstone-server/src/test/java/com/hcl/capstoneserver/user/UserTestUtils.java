package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.dto.ClientDTO;
import com.hcl.capstoneserver.user.dto.SupplierDTO;
import com.hcl.capstoneserver.user.entities.AppUser;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserTestUtils {
    @Autowired
    UserService userService;

    public List<SupplierDTO> createASupplier() {
        List<SupplierDTO> suppliers = new ArrayList<>();
        suppliers.add(userService.signUpSupplier(new Supplier(
                "supplier",
                "password",
                "supplier",
                "Colombo",
                "supplier@gmail.com",
                "071-2314531a",
                2.5f
        )));
        suppliers.add(userService.signUpSupplier(new Supplier(
                "supplier9",
                "password",
                "supplier2",
                "Colombo",
                "supplier2@gmail.com",
                "071-2314531a",
                2.5f
        )));
        return suppliers;
    }

    public List<ClientDTO> createAClient() {
        List<ClientDTO> clients = new ArrayList<>();
        clients.add(userService.signUpClient(new Client(
                "client",
                "password",
                "client",
                "Colombo",
                "client@gmail.com",
                "071-2314538",
                2.5f,
                1234567891
        )));
        clients.add(userService.signUpClient(new Client(
                "client2",
                "password",
                "client2",
                "Colombo",
                "client2@gmail.com",
                "071-2314538",
                2.5f,
                1234567891
        )));

        return clients;
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
