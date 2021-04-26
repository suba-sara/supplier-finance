package com.hcl.capstoneserver.user.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AppUser {
    @Id
    private String userId;

    private String password;
}
