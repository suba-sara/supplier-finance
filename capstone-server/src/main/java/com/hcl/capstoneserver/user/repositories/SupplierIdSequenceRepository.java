package com.hcl.capstoneserver.user.repositories;

import com.hcl.capstoneserver.user.entities.SupplierIdSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierIdSequenceRepository extends JpaRepository<SupplierIdSequence, String> {
}
