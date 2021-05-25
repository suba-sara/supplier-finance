package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.InvoiceDTO;
import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.UserTestUtils;
import com.hcl.capstoneserver.user.UserType;
import com.hcl.capstoneserver.user.repositories.ClientRepository;
import com.hcl.capstoneserver.user.repositories.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InvoiceControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

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

        InvoiceDTO invoice = new InvoiceDTO(
                "client",
                "supplier",
                1234567891,
                "2021-04-23",
                25000.0,
                0,
                CurrencyType.USD
        );

        webTestClient.post()
                     .uri(String.format("http://localhost:%d/api/invoices/create", port))
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(Mono.just(invoice), InvoiceDTO.class)
                     .exchange()
                     .expectStatus()
                     .is2xxSuccessful();
    }
}