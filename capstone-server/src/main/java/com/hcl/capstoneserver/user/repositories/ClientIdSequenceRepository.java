package com.hcl.capstoneserver.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientIdSequenceRepository extends JpaRepository<ClientIdSequenceRepository, String> {
}
