package com.hcl.capstoneserver.user;

import com.hcl.capstoneserver.account.AccountService;
import com.hcl.capstoneserver.seeder.DatabaseSeeder;
import com.hcl.capstoneserver.user.dto.BankerDTO;
import com.hcl.capstoneserver.user.dto.ClientDTO;
import com.hcl.capstoneserver.user.dto.PersonWithPasswordDTO;
import com.hcl.capstoneserver.user.dto.SupplierDTO;
import com.hcl.capstoneserver.user.entities.AppUser;
import com.hcl.capstoneserver.user.entities.Banker;
import com.hcl.capstoneserver.user.repositories.BankerRepository;
import com.hcl.capstoneserver.user.repositories.ClientRepository;
import com.hcl.capstoneserver.user.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserTestUtils {
    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Autowired
    DatabaseSeeder databaseSeeder;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BankerRepository bankerRepository;

    public void accountCreate() {
        databaseSeeder.seedTest();
        for (int i = 35; i < 40; i++) {
            accountService.getOTP("100002" + i);
        }
    }

    public List<SupplierDTO> createASupplier() {
        supplierRepository.deleteAll();
        List<SupplierDTO> suppliers = new ArrayList<>();
        suppliers.add(userService.signUpSupplier(new PersonWithPasswordDTO(
                "supplier",
                "supplier",
                "Colombo",
                "supplier@gmail.com",
                "071-2314531",
                "10000235",
                "password",
                "482410"
        )));
        suppliers.add(userService.signUpSupplier(new PersonWithPasswordDTO(
                "supplier9",
                "supplier2",
                "Colombo",
                "supplier2@gmail.com",
                "071-2314532",
                "10000236",
                "password",
                "482410"
        )));
        return suppliers;
    }

    public List<ClientDTO> createAClient() {
        clientRepository.deleteAll();
        List<ClientDTO> clients = new ArrayList<>();
        clients.add(userService.signUpClient(new PersonWithPasswordDTO(
                "client",
                "client",
                "Colombo",
                "client@gmail.com",
                "071-2314538",
                "10000237",
                "password",
                "482410"
        )));
        clients.add(userService.signUpClient(new PersonWithPasswordDTO(
                "client2",
                "client2",
                "Colombo",
                "client2@gmail.com",
                "071-2314538",
                "10000238",
                "password",
                "482410"
        )));

        return clients;
    }

    public List<BankerDTO> createBankers() {
        bankerRepository.deleteAll();
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
