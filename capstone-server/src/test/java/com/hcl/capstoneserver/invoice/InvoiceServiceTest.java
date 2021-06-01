package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.InvoiceDTO;
import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.UserTestUtils;
import com.hcl.capstoneserver.user.UserType;
import com.hcl.capstoneserver.user.dto.PersonWithPasswordDTO;
import com.hcl.capstoneserver.user.repositories.ClientRepository;
import com.hcl.capstoneserver.user.repositories.SupplierRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

@SpringBootTest
public class InvoiceServiceTest {
    @Autowired
    InvoiceService invoiceService;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    UserTestUtils userTestUtils;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ClientRepository clientRepository;

    @BeforeEach
    public void beforeEach() {
        invoiceRepository.deleteAll();
        supplierRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    @DisplayName("it should create new invoice")
    public void shouldCreateNewInvoice() {
        userTestUtils.createAUser(UserType.SUPPLIER);
        userTestUtils.createAUser(UserType.CLIENT);
        Assertions.assertNotNull(invoiceService.createInvoice(
                new InvoiceDTO(
                        "supplier",
                        1234567891,
                        "2021-04-23",
                        25000.0,
                        0,
                        CurrencyType.USD
                ), "client"));
    }
}