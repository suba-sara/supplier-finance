package com.hcl.capstoneserver.util;

import com.hcl.capstoneserver.user.entities.ClientIdSequence;
import com.hcl.capstoneserver.user.entities.SupplierIdSequence;
import com.hcl.capstoneserver.user.repositories.ClientIdSequenceRepository;
import com.hcl.capstoneserver.user.repositories.SupplierIdSequenceRepository;
import org.springframework.stereotype.Component;

@Component
public class SequenceGenerator {
    private final SupplierIdSequenceRepository supplierIdSequenceRepository;
    private final ClientIdSequenceRepository clientIdSequenceRepository;

    public SequenceGenerator(
            SupplierIdSequenceRepository supplierIdSequenceRepository,
            ClientIdSequenceRepository clientIdSequenceRepository
    ) {
        this.supplierIdSequenceRepository = supplierIdSequenceRepository;
        this.clientIdSequenceRepository = clientIdSequenceRepository;
    }


    public String getSupplierSequence() {
        SupplierIdSequence generated = supplierIdSequenceRepository.save(new SupplierIdSequence());
        String id = generated.getId();
        supplierIdSequenceRepository.delete(generated);
        return id;
    }

    public String getClientSequence() {
        ClientIdSequence generated = clientIdSequenceRepository.save(new ClientIdSequence());
        String id = generated.getId();
        clientIdSequenceRepository.delete(generated);
        return id;
    }
}
