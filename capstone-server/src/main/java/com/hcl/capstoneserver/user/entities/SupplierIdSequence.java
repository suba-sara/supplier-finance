package com.hcl.capstoneserver.user.entities;

import com.hcl.capstoneserver.generator.id.CustomIdGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SupplierIdSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suppler_id_sequence")
    @GenericGenerator(name = "suppler_id_sequence",
            strategy = "com.hcl.capstoneserver.generator.id.CustomIdGenerator",
            parameters = {
                    @Parameter(name = CustomIdGenerator.SEQUENCE_PARAM, value = "suppler_id_sequence"),
                    @Parameter(name = CustomIdGenerator.INITIAL_PARAM, value = "1"),
                    @Parameter(name = CustomIdGenerator.OPT_PARAM, value = "pooled-lo"),
                    @Parameter(name = CustomIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = CustomIdGenerator.PREFIX_PARAM, value = "SP_"),
                    @Parameter(name = CustomIdGenerator.NUMBER_FORMAT_PARAM, value = "%05d")
            }
    )
    private String id;

    public SupplierIdSequence() {
    }

    public SupplierIdSequence(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
