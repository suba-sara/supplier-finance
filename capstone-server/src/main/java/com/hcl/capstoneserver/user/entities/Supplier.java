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
public class Supplier extends Person {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suppler_id_sequence")
    @GenericGenerator(name = "suppler_id_sequence",
            strategy = "com.hcl.capstoneserver.generator.id.CustomIdGenerator",
            parameters = {
                    @Parameter(name = CustomIdGenerator.SEQUENCE_PARAM, value = "suppler_id_sequence"),
                    @Parameter(name = CustomIdGenerator.INITIAL_PARAM, value = "1"),
                    @Parameter(name = CustomIdGenerator.OPT_PARAM, value = "pooled-lo"),
                    @Parameter(name = CustomIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = CustomIdGenerator.PREFIX_PARAM, value = "SP_"),
                    @Parameter(name = CustomIdGenerator.NUMBER_FORMAT_PARAM, value = "%05d")
            }
    )
    @Column(unique = true)
    private String supplierId;

    public Supplier() {
    }

    public Supplier(String userId, String password, String name, String address, String email, String phone, Float interestRate) {
        super(userId, password, UserType.SUPPLIER, name, address, email, phone, interestRate);
    }


    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId=" + supplierId +
                "} " + super.toString();
    }
}
