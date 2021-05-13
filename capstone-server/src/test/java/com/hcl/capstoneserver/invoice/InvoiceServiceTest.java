package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
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
    @DisplayName("it should create new invoice")
    public void shouldCreateNewInvoice() {
        Assertions.assertNotNull(invoiceService.createInvoice(
                new InvoiceDTO(
                        new Client(
                                "shel",
                                "sdfdsfds",
                                "Sheldon",
                                "Colombo",
                                "shel@gmail.com",
                                "071-2314538",
                                2.5f,
                                "1001",
                                1234567891
                        ),
                        new Supplier(
                                "sup1",
                                "password",
                                "ma",
                                "konoha",
                                "madara@konoha.org",
                                "123456",
                                5.0F,
                                "s001"
                        ),
                        1234567891,
                        "2021-04-23",
                        25000.23,
                        1
                )));
    }
}