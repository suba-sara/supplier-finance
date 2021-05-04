package com.hcl.capstoneserver.util;

import com.hcl.capstoneserver.user.entities.SupplierIdSequence;
import com.hcl.capstoneserver.user.repositories.SupplierIdSequenceRepository;
import org.springframework.stereotype.Component;

@Component
public class SequenceGenerator {
    private  final SupplierIdSequenceRepository supplierIdSequenceRepository;

    public SequenceGenerator(SupplierIdSequenceRepository supplierIdSequenceRepository) {
        this.supplierIdSequenceRepository = supplierIdSequenceRepository;
    }


    public  String getSupplierSequence() {
        SupplierIdSequence generated = supplierIdSequenceRepository.save(new SupplierIdSequence());
        String id = generated.getId();
        supplierIdSequenceRepository.delete(generated);
        return id;
    }
}
