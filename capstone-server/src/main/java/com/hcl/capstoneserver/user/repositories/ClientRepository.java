package com.hcl.capstoneserver.user.repositories;

import com.hcl.capstoneserver.user.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    @Query("SELECT CASE WHEN COUNT(c)>0 THEN true ELSE false END FROM Client AS c WHERE c.email = ?1")
    Boolean existsByEmail(String email);
}
