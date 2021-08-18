package com.hcl.capstoneserver.user.repositories;

import com.hcl.capstoneserver.user.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {
    Boolean existsSupplierByEmail(String email);
}