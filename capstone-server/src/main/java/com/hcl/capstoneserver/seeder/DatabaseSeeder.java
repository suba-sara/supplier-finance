package com.hcl.capstoneserver.seeder;

import com.hcl.capstoneserver.account.entity.Account;
import com.hcl.capstoneserver.account.repositories.AccountRepository;
import com.hcl.capstoneserver.user.entities.Banker;
import com.hcl.capstoneserver.user.repositories.BankerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder {
    private final BankerRepository bank;
    private final AccountRepository account;

    @Autowired
    public DatabaseSeeder(BankerRepository bank, AccountRepository account) {
        this.bank = bank;
        this.account = account;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedAccountTable();
        seedBankTable();
    }

    private void seedBankTable() {
        if (this.bank.findAll().size() == 0) {
            List<Banker> bankerList = new ArrayList<>();
            bankerList.add(new Banker("shsbank1", "123456@shs", "EM_00001"));
            bankerList.add(new Banker("shsbank2", "123456@shs", "EM_00002"));
            this.bank.saveAll(bankerList);
        }
    }

    private void seedAccountTable() {
        if (this.account.findAll().size() == 0) {
            List<Account> accountList = new ArrayList<>();
            accountList.add(new Account(1234567891, "Shevan Fernando", "w.k.b.s.t.fernando@gmail.com"));
            accountList.add(new Account(1234567892, "Shashi Sri Dharmasiri", "shashi@gmail.com"));
            accountList.add(new Account(1234567893, "Mohomed Jesse", "jessel@gmail.com"));
            this.account.saveAll(accountList);
        }
    }
}
