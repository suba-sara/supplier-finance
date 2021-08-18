package com.hcl.capstoneserver.user.repositories;

import com.hcl.capstoneserver.user.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Boolean existsClientByEmail(String email);
}
