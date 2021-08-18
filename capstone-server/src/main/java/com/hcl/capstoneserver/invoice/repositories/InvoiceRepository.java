package com.hcl.capstoneserver.invoice.repositories;

import com.hcl.capstoneserver.invoice.InvoiceStatus;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer>, JpaSpecificationExecutor<Invoice> {
    int countAllByClientAndStatus(Client client, InvoiceStatus status);

    int countAllBySupplierAndStatus(Supplier supplier, InvoiceStatus status);

    int countAllByStatus(InvoiceStatus status);
}
