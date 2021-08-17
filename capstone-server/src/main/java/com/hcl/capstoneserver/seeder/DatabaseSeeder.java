package com.hcl.capstoneserver.seeder;

import com.hcl.capstoneserver.account.entity.Account;
import com.hcl.capstoneserver.account.repositories.AccountRepository;
import com.hcl.capstoneserver.user.entities.Banker;
import com.hcl.capstoneserver.user.repositories.BankerRepository;
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

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedAccountTable();
        seedBankTable();
    }

    private void seedBankTable() {
        if (this.bankerRepository.findAll().size() == 0) {
            List<Banker> bankerList = new ArrayList<>();
            bankerList.add(new Banker("shsbank1", "123456@shs", "EM_00001"));
            bankerList.add(new Banker("shsbank2", "123456@shs", "EM_00002"));
            this.bankerRepository.saveAll(bankerList);
        }
    }

    private void seedAccountTable() {
        if (this.accountRepository.findAll().size() == 0) {
            List<Account> accountList = new ArrayList<>();
            accountList.add(new Account(1234567891, "Shevan Fernando", "w.k.b.s.t.fernando@gmail.com"));
            accountList.add(new Account(1234567892, "Shashi Sri Dharmasiri", "shashi@gmail.com"));
            accountList.add(new Account(1234567893, "Mohomed Jesse", "jessel@gmail.com"));
            this.accountRepository.saveAll(accountList);
        }
    }
}
