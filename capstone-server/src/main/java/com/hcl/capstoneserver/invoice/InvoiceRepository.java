package com.hcl.capstoneserver.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query("SELECT i FROM Invoice i WHERE i.invoiceNumber = ?1")
    boolean findInvoiceByInvoiceNumber(Integer invoiceNumber);
}
