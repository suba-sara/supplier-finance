package com.hcl.capstoneserver.user.entities;

import com.hcl.capstoneserver.generator.id.CustomIdGenerator;
import com.hcl.capstoneserver.user.UserType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Client extends Person {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_id_sequence")
    @GenericGenerator(name = "client_id_sequence",
            strategy = "com.hcl.capstoneserver.generator.id.CustomIdGenerator",
            parameters = {
                    @Parameter(name = CustomIdGenerator.SEQUENCE_PARAM, value = "client_id_sequence"),
                    @Parameter(name = CustomIdGenerator.INITIAL_PARAM, value = "1"),
                    @Parameter(name = CustomIdGenerator.OPT_PARAM, value = "pooled-lo"),
                    @Parameter(name = CustomIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = CustomIdGenerator.PREFIX_PARAM, value = "CL_"),
                    @Parameter(name = CustomIdGenerator.NUMBER_FORMAT_PARAM, value = "%05d")
            }
    )
    @Column(unique = true)
    private int clientId;

    private int accountNumber;

    public Client() {
    }

    public Client(String userId, String password, String name, String address, String email, String phone, Float interestRate, int clientId, int accountNumber) {
        super(userId, password, UserType.CLIENT, name, address, email, phone, interestRate);
        this.clientId = clientId;
        this.accountNumber = accountNumber;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", accountNumber=" + accountNumber +
                "} " + super.toString();
    }
}
