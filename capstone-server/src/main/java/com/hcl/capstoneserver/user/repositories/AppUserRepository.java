package com.hcl.capstoneserver.user.repositories;

import com.hcl.capstoneserver.user.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
}
