package com.hcl.capstoneserver.user.repositories;

import com.hcl.capstoneserver.user.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, String> {
}
