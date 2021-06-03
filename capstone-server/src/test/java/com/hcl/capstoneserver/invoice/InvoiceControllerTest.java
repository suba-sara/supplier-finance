package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.UserService;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

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

    @Autowired
    UserService userService;

    @BeforeEach
    public void beforeEach() {
        invoiceRepository.deleteAll();
        supplierRepository.deleteAll();
        clientRepository.deleteAll();
    }

//    @Test
//    @DisplayName("it should create new invoice")
//    public void shouldCreateNewInvoice() {
//        userTestUtils.createAUser(UserType.SUPPLIER);
//        userTestUtils.createAUser(UserType.CLIENT);
//
//        String token = userTestUtils.loginAUser(UserType.CLIENT);
//
//        CreateInvoiceDTO invoice = new CreateInvoiceDTO(
//                "supplier",
//                "1234567891",
//                LocalDate.now().toString(),
//                25000.0,
//                CurrencyType.USD
//        );
//
//        webTestClient.post()
//                     .uri(String.format("http://localhost:%d/api/invoices/create", port))
//                     .header(HttpHeaders.AUTHORIZATION, token)
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(Mono.just(invoice), CreateInvoiceDTO.class)
//                     .exchange()
//                     .expectStatus()
//                     .is2xxSuccessful();
//    }
}