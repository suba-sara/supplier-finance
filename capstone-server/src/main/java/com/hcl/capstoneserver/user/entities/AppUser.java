package com.hcl.capstoneserver.user.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser {
    @Id
    private String userId;

    private String password;

    public AppUser() {
    }

    public AppUser(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public AppUser(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
