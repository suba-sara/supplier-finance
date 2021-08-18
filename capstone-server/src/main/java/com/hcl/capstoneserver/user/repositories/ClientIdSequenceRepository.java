package com.hcl.capstoneserver.user.repositories;

import com.hcl.capstoneserver.user.entities.ClientIdSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientIdSequenceRepository extends JpaRepository<ClientIdSequence, String> {
}
