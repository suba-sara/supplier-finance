package com.hcl.capstoneserver.user.repositories;

import com.hcl.capstoneserver.user.entities.Banker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankerRepository extends JpaRepository<Banker, String> {
}
