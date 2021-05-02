package com.hcl.capstoneserver.user.entities;

import com.hcl.capstoneserver.user.UserType;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser {
    @Id
    private String userId;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    public AppUser() {
    }

    public AppUser(String userId) {
        this.userId = userId;
    }

    public AppUser(String userId, String password) {
        this.userId = userId;
        this.password = password;
        this.userType = UserType.USER;
    }

    public AppUser(String userId, String password, UserType userType) {
        this.userId = userId;
        this.password = password;
        this.userType = userType;
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

    public String getUserType() {
        return userType.toString();
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
