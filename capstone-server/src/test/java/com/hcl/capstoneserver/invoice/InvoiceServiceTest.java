package com.hcl.capstoneserver.invoice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

@SpringBootTest
public class InvoiceServiceTest {
    @Autowired
    InvoiceService invoiceService;

    @Test
    @DisplayName("it shoudl create new invoice")
    public void shouldCreateNewInvoice() {
        Assertions.assertNotNull(invoiceService.createInvoice(
                new InvoiceDTO(
                        null,
                        null,
                        1234567891,
                        Date.valueOf("2021-04-23"),
                        25000.23,
                        1
                )));
    }
}