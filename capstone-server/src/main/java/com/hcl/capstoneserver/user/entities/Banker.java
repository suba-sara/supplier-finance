package com.hcl.capstoneserver.user.entities;

import com.hcl.capstoneserver.user.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Banker extends AppUser {
    @Column(unique = true)
    public String employeeId;

    public Banker() {
    }

    public Banker(String userId, String password, String employeeId) {
        super(userId, password, UserType.BANKER);
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
