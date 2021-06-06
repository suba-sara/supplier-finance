package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.ClientViewInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.CreateInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.StatusUpdateInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.UpdateInvoiceDTO;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.UserService;
import com.hcl.capstoneserver.user.UserTestUtils;
import com.hcl.capstoneserver.user.UserType;
import com.hcl.capstoneserver.user.dto.ClientDTO;
import com.hcl.capstoneserver.user.dto.SupplierDTO;
import com.hcl.capstoneserver.user.repositories.ClientRepository;
import com.hcl.capstoneserver.user.repositories.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InvoiceControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserTestUtils userTestUtils;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    InvoiceTestUtils invoiceTestUtils;

    List<ClientViewInvoiceDTO> createInvoice; // invoiceNumber : 1234567898, 1234567899
    List<SupplierDTO> suppliers;
    List<ClientDTO> clients;
    Invoice expiredInvoice; // invoiceNumber : 999999999
    String client1token;
    String client2token;

    @Test
    void contextLoads() {
        assertNotNull(invoiceService);
        assertNotNull(userTestUtils);
        assertNotNull(invoiceTestUtils);
    }

    @BeforeEach
    public void beforeEach() {
        invoiceRepository.deleteAll();
        supplierRepository.deleteAll();
        clientRepository.deleteAll();

        suppliers = userTestUtils.createASupplier();
        clients = userTestUtils.createAClient();
        createInvoice = invoiceTestUtils.createInvoice(suppliers);
        expiredInvoice = invoiceTestUtils.createExpiredInvoice(suppliers, clients);

        client1token = userTestUtils.loginAUser(UserType.CLIENT, "client");
        client2token = userTestUtils.loginAUser(UserType.CLIENT, "client2");
    }

    private void updateInvoiceStatus(InvoiceStatus status, Integer invoiceId) {
        invoiceService.statusUpdate(new StatusUpdateInvoiceDTO(
                invoiceId,
                status
        ), "BANK");
    }

    @Nested
    @DisplayName("invoice create test")
    class InvoiceCreateTests {
        @Test
        @DisplayName("it should create new invoice")
        public void shouldCreateNewInvoice() {
            CreateInvoiceDTO dto = new CreateInvoiceDTO(
                    suppliers.get(0).getSupplierId(),
                    "1234567891",
                    LocalDate.now(),
                    25000.0,
                    CurrencyType.USD
            );

            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/invoices/create", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .header(HttpHeaders.AUTHORIZATION, client1token)
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(Mono.just(dto), CreateInvoiceDTO.class)
                         .exchange()
                         .expectStatus()
                         .is2xxSuccessful();
        }

        @Test
        @DisplayName("it should not create new invoice with exists invoice number")
        public void shouldNotCreateNewInvoiceWithSameInvoiceNumber() {
            CreateInvoiceDTO dto = new CreateInvoiceDTO(
                    suppliers.get(0).getSupplierId(),
                    "1234567898",
                    LocalDate.now(),
                    25000.0,
                    CurrencyType.USD
            );
            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/invoices/create", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .header(HttpHeaders.AUTHORIZATION, client1token)
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(Mono.just(dto), CreateInvoiceDTO.class)
                         .exchange()
                         .expectStatus()
                         .isBadRequest()
                         .expectBody()
                         .jsonPath("$.errors[0].message")
                         .isEqualTo("400 An invoice number already exists for this supplier.");
        }

        @Test
        @DisplayName("it should not create new invoice with old date")
        public void shouldNotCreateNewInvoiceWithOldDate() {
            CreateInvoiceDTO dto = new CreateInvoiceDTO(
                    suppliers.get(0).getSupplierId(),
                    "1234567892",
                    LocalDate.parse("2021-04-05"),
                    25000.0,
                    CurrencyType.USD
            );
            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/invoices/create", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .header(HttpHeaders.AUTHORIZATION, client1token)
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(Mono.just(dto), CreateInvoiceDTO.class)
                         .exchange()
                         .expectStatus()
                         .isBadRequest()
                         .expectBody()
                         .jsonPath("$.errors[0].message")
                         .isEqualTo("400 The invoice date is an older date.");
        }

        @Test
        @DisplayName("it should not create new invoice with not exists supplier")
        public void shouldNotCreateNewInvoiceWithNotExistsSupplier() {
            CreateInvoiceDTO dto = new CreateInvoiceDTO(
                    "SP_1",
                    "1234567893",
                    LocalDate.now(),
                    25000.0,
                    CurrencyType.USD
            );
            webTestClient.post()
                         .uri(String.format("http://localhost:%d/api/invoices/create", port))
                         .contentType(MediaType.APPLICATION_JSON)
                         .header(HttpHeaders.AUTHORIZATION, client1token)
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(Mono.just(dto), CreateInvoiceDTO.class)
                         .exchange()
                         .expectStatus()
                         .is4xxClientError()
                         .expectBody()
                         .jsonPath("$.errors[0].message")
                         .isEqualTo("400 This SUPPLIER is not exist.");
        }
    }

    @Nested
    @DisplayName("invoice update test")
    class InvoiceUpdateTests {

        // Bank
        // One feature needs to be tested when BANK user is created: invoice status can update only by BANK
        @Nested
        @DisplayName("invoice update test: BANK")
        class InvoiceUpdateBankTests {

            @Test
            @DisplayName("it should update the invoice status")
            public void shouldUpdateInvoiceStatus() {
                StatusUpdateInvoiceDTO dto = new StatusUpdateInvoiceDTO(
                        createInvoice.get(0).getInvoiceId(),
                        InvoiceStatus.IN_REVIEW
                );

                webTestClient.put()
                             .uri(String.format("http://localhost:%d/api/invoices/update/status", port))
                             .contentType(MediaType.APPLICATION_JSON)
                             .header(HttpHeaders.AUTHORIZATION, client1token)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(Mono.just(dto), StatusUpdateInvoiceDTO.class)
                             .exchange()
                             .expectStatus()
                             .is2xxSuccessful();
            }

            @Test
            @DisplayName("it should not update when invoice is expired")
            public void shouldNotUpdateInvoiceWhenInvoiceIsExpired() {
                StatusUpdateInvoiceDTO dto = new StatusUpdateInvoiceDTO(
                        expiredInvoice.getInvoiceId(),
                        InvoiceStatus.IN_REVIEW
                );

                webTestClient.put()
                             .uri(String.format("http://localhost:%d/api/invoices/update/status", port))
                             .contentType(MediaType.APPLICATION_JSON)
                             .header(HttpHeaders.AUTHORIZATION, client1token)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(Mono.just(dto), StatusUpdateInvoiceDTO.class)
                             .exchange()
                             .expectStatus()
                             .is4xxClientError()
                             .expectBody()
                             .jsonPath("$.errors[0].message")
                             .isEqualTo("400 You can not update the invoice status, because invoice is expire.");
            }

            @Test
            @DisplayName("it should not update when invoice status is REJECTED")
            public void shouldNotUpdateInvoiceWhenStatusIsRejected() {
                updateInvoiceStatus(InvoiceStatus.REJECTED, createInvoice.get(0).getInvoiceId());
                StatusUpdateInvoiceDTO dto = new StatusUpdateInvoiceDTO(
                        createInvoice.get(0).getInvoiceId(),
                        InvoiceStatus.IN_REVIEW
                );

                webTestClient.put()
                             .uri(String.format("http://localhost:%d/api/invoices/update/status", port))
                             .contentType(MediaType.APPLICATION_JSON)
                             .header(HttpHeaders.AUTHORIZATION, client1token)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(Mono.just(dto), StatusUpdateInvoiceDTO.class)
                             .exchange()
                             .expectStatus()
                             .is4xxClientError()
                             .expectBody()
                             .jsonPath("$.errors[0].message")
                             .isEqualTo("400 This invoice can not update, because invoice is REJECTED.");
            }

            //Client
            @Nested
            @DisplayName("invoice update test: CLIENT")
            class InvoiceUpdateClientTests {
                @Test
                @DisplayName("it should update invoice")
                public void shouldUpdateInvoice() {
                    UpdateInvoiceDTO dto = new UpdateInvoiceDTO(
                            createInvoice.get(0).getInvoiceId(),
                            suppliers.get(0).getSupplierId(),
                            "1234567894",
                            LocalDate.now(),
                            25000.0,
                            CurrencyType.USD
                    );
                    webTestClient.put()
                                 .uri(String.format("http://localhost:%d/api/invoices/update", port))
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .header(HttpHeaders.AUTHORIZATION, client1token)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(Mono.just(dto), UpdateInvoiceDTO.class)
                                 .exchange()
                                 .expectStatus()
                                 .is2xxSuccessful();
                }

                @Test
                @DisplayName("it should not update invoice when that invoice owner is not a same client")
                public void shouldNotUpdateInvoiceWhenInvoiceOwnerIsNotEqual() {
                    UpdateInvoiceDTO dto = new UpdateInvoiceDTO(
                            createInvoice.get(1).getInvoiceId(),
                            suppliers.get(0).getSupplierId(),
                            "1234567898",
                            LocalDate.now(),
                            25000.0,
                            CurrencyType.USD
                    );
                    webTestClient.put()
                                 .uri(String.format("http://localhost:%d/api/invoices/update", port))
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .header(HttpHeaders.AUTHORIZATION, client1token)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(Mono.just(dto), UpdateInvoiceDTO.class)
                                 .exchange()
                                 .expectStatus()
                                 .is4xxClientError()
                                 .expectBody()
                                 .jsonPath("$.errors[0].message")
                                 .isEqualTo("400 client you do not have permission to update this invoice.");
                }

                @Test
                @DisplayName("it should not update invoice with old date")
                public void shouldNotUpdateInvoiceWithOldDate() {
                    UpdateInvoiceDTO dto = new UpdateInvoiceDTO(
                            createInvoice.get(0).getInvoiceId(),
                            suppliers.get(0).getSupplierId(),
                            "1234567892",
                            LocalDate.parse("2021-04-05"),
                            25000.0,
                            CurrencyType.USD
                    );
                    webTestClient.put()
                                 .uri(String.format("http://localhost:%d/api/invoices/update", port))
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .header(HttpHeaders.AUTHORIZATION, client1token)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(Mono.just(dto), UpdateInvoiceDTO.class)
                                 .exchange()
                                 .expectStatus()
                                 .is4xxClientError()
                                 .expectBody()
                                 .jsonPath("$.errors[0].message")
                                 .isEqualTo("400 The invoice date is an older date.");
                }

                @Test
                @DisplayName("it should not update invoice with invoice status In_Review, Approved and Rejected")
                public void shouldNotUpdateInvoiceWithInReviewAndApprovedAndRejected() {
                    updateInvoiceStatus(InvoiceStatus.IN_REVIEW, createInvoice.get(0).getInvoiceId());
                    UpdateInvoiceDTO dto = new UpdateInvoiceDTO(
                            createInvoice.get(0).getInvoiceId(),
                            suppliers.get(0).getSupplierId(),
                            "1234567892",
                            LocalDate.now(),
                            25000.0,
                            CurrencyType.EUR
                    );
                    webTestClient.put()
                                 .uri(String.format("http://localhost:%d/api/invoices/update", port))
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .header(HttpHeaders.AUTHORIZATION, client1token)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(Mono.just(dto), UpdateInvoiceDTO.class)
                                 .exchange()
                                 .expectStatus()
                                 .is4xxClientError()
                                 .expectBody()
                                 .jsonPath("$.errors[0].message")
                                 .isEqualTo("400 This invoice can not update, because invoice is IN_REVIEW.");
                }
            }
        }

        @Nested
        @DisplayName("invoice delete test")
        class InvoiceDeleteTest {
            @Test
            @DisplayName("it should delete invoice")
            public void shouldDeleteInvoice() {
                webTestClient.delete()
                             .uri(String.format(
                                     "http://localhost:%d/api/invoices/delete/%d",
                                     port,
                                     createInvoice.get(0).getInvoiceId()
                             ))
                             .header(HttpHeaders.AUTHORIZATION, client1token)
                             .exchange()
                             .expectStatus()
                             .is2xxSuccessful();
            }

            // Invoice can delete client only, suppliers and bank can not delete
            // This test checks the above point and below point (Display Name mentioned thing)
            @Test
            @DisplayName("it should not delete invoice when that invoice owner is not a same client or user")
            public void shouldNotDeleteInvoiceWhenInvoiceOwnerIsNotEqual() {
                webTestClient.delete()
                             .uri(String.format(
                                     "http://localhost:%d/api/invoices/delete/%d",
                                     port,
                                     createInvoice.get(0).getInvoiceId()
                             ))
                             .header(HttpHeaders.AUTHORIZATION, client2token)
                             .exchange()
                             .expectStatus()
                             .is4xxClientError()
                             .expectBody()
                             .jsonPath("$.errors[0].message")
                             .isEqualTo("400 client2 you do not have permission to delete this invoice.");
            }

            @Test
            @DisplayName("it should not delete invoice with invoice status In_Review, Approved and Rejected")
            public void shouldNotDeleteInvoiceWithInReviewAndApprovedAndRejected() {
                updateInvoiceStatus(InvoiceStatus.IN_REVIEW, createInvoice.get(0).getInvoiceId());
                webTestClient.delete()
                             .uri(String.format(
                                     "http://localhost:%d/api/invoices/delete/%d",
                                     port,
                                     createInvoice.get(0).getInvoiceId()
                             ))
                             .header(HttpHeaders.AUTHORIZATION, client1token)
                             .exchange()
                             .expectStatus()
                             .is4xxClientError()
                             .expectBody()
                             .jsonPath("$.errors[0].message")
                             .isEqualTo("400 This invoice can not delete, because invoice is IN_REVIEW.");
            }
        }

        @Nested
        @DisplayName("invoice retrieve test")
        class InvoiceRetrieveTest {
            @Test
            @DisplayName("it should return all invoice")
            public void shouldReturnAllInvoice() {
                webTestClient.get()
                             .uri(String.format("http://localhost:%d/api/invoices/fetchAllInvoices", port))
                             .exchange()
                             .expectStatus()
                             .is2xxSuccessful();
            }
        }
    }
}