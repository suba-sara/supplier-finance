package com.hcl.capstoneserver.seeder;

import com.hcl.capstoneserver.account.entity.Account;
import com.hcl.capstoneserver.account.repositories.AccountRepository;
import com.hcl.capstoneserver.user.entities.Banker;
import com.hcl.capstoneserver.user.repositories.BankerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder {
    private final BankerRepository bankerRepository;
    private final AccountRepository accountRepository;

    public DatabaseSeeder(BankerRepository bankerRepository, AccountRepository accountRepository) {
        this.bankerRepository = bankerRepository;
        this.accountRepository = accountRepository;
    }
    final static String _accNumberBase = "10000000";
    @Autowired
    private BankerRepository bankerRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Value("${seed.account.data}")
    private boolean seedAccountData;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        if (seedAccountData) {
            seedAccountTable();
            seedBankerTable();
        }
    }

    private void seedBankerTable() {
        if (this.bankerRepository.count() == 0) {
            List<Banker> bankerList = new ArrayList<>();
            bankerList.add(new Banker("shsbank1", "123456@shs", "EM_00001"));
            bankerList.add(new Banker("shsbank2", "123456@shs", "EM_00002"));
            this.bankerRepository.saveAll(bankerList);
        }
    }

    private String _generateAccNumber(int n) throws RuntimeException {
        String newPart = String.valueOf(n);
        if (newPart.length() > _accNumberBase.length()) {
            throw new RuntimeException("Maximum no of accounts exceeded");
        }

        StringBuilder accNumber = new StringBuilder(_accNumberBase);
        for (int i = 0; i < newPart.length(); i++) {
            accNumber.setCharAt(_accNumberBase.length() - 1 - i, newPart.charAt(newPart.length() - 1 - i));
        }

        return accNumber.toString();
    }

    private void seedAccountTable() {
        if (this.accountRepository.count() == 0) {
            List<Account> accountList = new ArrayList<>();

            for (int i = 1; i <= 10000; i++) {
                String accNumber = _generateAccNumber(i);
                accountList.add(new Account(
                        accNumber,
                        "ACO_" + accNumber,
                        "shs_" + accNumber + "@mailinator.com",
                        2.5F
                ));
            }
            this.accountRepository.saveAll(accountList);
        }
    }
}
