package com.hcl.capstoneserver.user.repositories;

import com.hcl.capstoneserver.user.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupplierRepository extends JpaRepository<Supplier, String> {

    @Query("SELECT CASE WHEN COUNT(s)>0 THEN true ELSE false END FROM Supplier AS s WHERE s.email = ?1 ")
    Boolean existsByEmail(String email);
}