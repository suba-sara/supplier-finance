package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.InvoiceDTO;
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
    UserTestUtils userTestUtils;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ClientRepository clientRepository;

    @BeforeEach
    public void beforeEach() {
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
                        "client",
                        "supplier",
                        1234567891,
                        "2021-04-23",
                        25000.23,
                        0,
                        CurrencyType.USD
                )));
    }
}