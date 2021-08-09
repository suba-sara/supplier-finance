package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.user.dto.BankerDTO;
import com.hcl.capstoneserver.user.dto.ClientDTO;
import com.hcl.capstoneserver.user.dto.SupplierDTO;
import com.hcl.capstoneserver.user.entities.AppUser;
import com.hcl.capstoneserver.user.entities.Banker;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public List<BankerDTO> createBankers() {
        List<BankerDTO> bankers = new ArrayList<>();
        bankers.add(userService.createBanker(new Banker(
                "banker1",
                "password",
                "E10001"
        )));
        bankers.add(userService.createBanker(new Banker(
                "banker2",
                "password",
                "E10002"
        )));

        return bankers;
    }

    public String loginAUser(UserType userType, String username) {
        String token = "Bearer ";
        switch (userType) {
            case CLIENT:
                token += userService.signIn(new AppUser(username, "password", UserType.CLIENT)).getJwt();
                break;
            case SUPPLIER:
                token += userService.signIn(new AppUser(username, "password", UserType.SUPPLIER)).getJwt();
                break;
            case BANKER:
                token += userService.signIn(new AppUser(username, "password", UserType.BANKER)).getJwt();

        }
        return token;
    }
}
