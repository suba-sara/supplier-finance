package com.hcl.capstoneserver.user.repositories;

import com.hcl.capstoneserver.user.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
}
