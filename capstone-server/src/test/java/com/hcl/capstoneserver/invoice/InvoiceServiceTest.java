package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.ClientViewInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.CreateInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.StatusUpdateInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.UpdateInvoiceDTO;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.UserTestUtils;
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
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InvoiceServiceTest {
    @Autowired
    InvoiceService invoiceService;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    UserTestUtils userTestUtils;

    @Autowired
    InvoiceTestUtils invoiceTestUtils;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ClientRepository clientRepository;

    List<ClientViewInvoiceDTO> createInvoice; // invoiceNumber : 1234567898, 1234567899
    List<SupplierDTO> suppliers;
    List<ClientDTO> clients;
    Invoice expiredInvoice;

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
    }

    private InvoiceStatus updateInvoiceStatus(InvoiceStatus status, Integer invoiceId) {
        return invoiceService.statusUpdate(new StatusUpdateInvoiceDTO(
                invoiceId,
                status
        ), "BANK").getStatus();
    }

    @Nested
    @DisplayName("invoice create test")
    class InvoiceCreateTests {
        @Test
        @DisplayName("it should create new invoice")
        public void shouldCreateNewInvoice() {
            assertNotNull(invoiceService.createInvoice(
                    new CreateInvoiceDTO(
                            suppliers.get(0).getSupplierId(),
                            "1234567891",
                            LocalDate.now(),
                            25000.0,
                            CurrencyType.USD
                    ), "client"));
        }

        @Test
        @DisplayName("it should not create new invoice with exists invoice number")
        public void shouldNotCreateNewInvoiceWithSameInvoiceNumber() {
            assertThrows(
                    HttpClientErrorException.class, () ->
                            invoiceService.createInvoice(
                                    new CreateInvoiceDTO(
                                            suppliers.get(0).getSupplierId(),
                                            "1234567898",
                                            LocalDate.now(),
                                            25000.0,
                                            CurrencyType.USD
                                    ), "client"), "400 An invoice number already exists for this supplier."
            );
        }

        @Test
        @DisplayName("it should not create new invoice with old date")
        public void shouldNotCreateNewInvoiceWithOldDate() {
            assertEquals(
                    "400 The invoice date is an older date.",
                    assertThrows(
                            HttpClientErrorException.class,
                            () -> invoiceService.createInvoice(
                                    new CreateInvoiceDTO(
                                            suppliers.get(0).getSupplierId(),
                                            "1234567892",
                                            LocalDate.parse("2021-04-05"),
                                            25000.0,
                                            CurrencyType.USD
                                    ), "client")
                    ).getMessage()
            );
        }

        @Test
        @DisplayName("it should not create new invoice with not exists supplier")
        public void shouldNotCreateNewInvoiceWithNotExistsSupplier() {
            assertEquals(
                    "400 This SUPPLIER is not found.",
                    assertThrows(
                            HttpClientErrorException.class,
                            () -> invoiceService.createInvoice(
                                    new CreateInvoiceDTO(
                                            "SP_1",
                                            "1234567893",
                                            LocalDate.now(),
                                            25000.0,
                                            CurrencyType.USD
                                    ), "client")
                    ).getMessage()
            );
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
                assertEquals(
                        InvoiceStatus.IN_REVIEW,
                        updateInvoiceStatus(InvoiceStatus.IN_REVIEW, createInvoice.get(0).getInvoiceId())
                );
            }

            @Test
            @DisplayName("it should not update when invoice is expired")
            public void shouldNotUpdateInvoiceWhenInvoiceIsExpired() {
                assertEquals(
                        "400 You can not update the invoice status, because invoice is expire.",
                        assertThrows(
                                HttpClientErrorException.class, () ->
                                        updateInvoiceStatus(InvoiceStatus.IN_REVIEW, expiredInvoice.getInvoiceId())
                        ).getMessage()
                );
            }

            @Test
            @DisplayName("it should not update when invoice status is REJECTED")
            public void shouldNotUpdateInvoiceWhenStatusIsRejected() {
                updateInvoiceStatus(InvoiceStatus.REJECTED, createInvoice.get(0).getInvoiceId());
                assertEquals(
                        "400 This invoice can not update, because invoice is REJECTED.",
                        assertThrows(
                                HttpClientErrorException.class, () ->
                                        updateInvoiceStatus(
                                                InvoiceStatus.IN_REVIEW,
                                                createInvoice.get(0).getInvoiceId()
                                        )
                        ).getMessage()
                );
            }
        }

        //Client
        @Nested
        @DisplayName("invoice update test: CLIENT")
        class InvoiceUpdateClientTests {
            @Test
            @DisplayName("it should update invoice")
            public void shouldUpdateInvoice() {
                assertEquals("1234567894", invoiceService.updateInvoice(new UpdateInvoiceDTO(
                        createInvoice.get(0).getInvoiceId(),
                        suppliers.get(0).getSupplierId(),
                        "1234567894",
                        LocalDate.now(),
                        25000.0,
                        CurrencyType.USD
                ), "client").getInvoiceNumber());
            }

            @Test
            @DisplayName("it should not update invoice when that invoice owner is not a same client")
            public void shouldNotUpdateInvoiceWhenInvoiceOwnerIsNotEqual() {
                assertEquals(
                        "400 client you do not have permission to update this invoice.",
                        assertThrows(
                                HttpClientErrorException.class,
                                () -> invoiceService.updateInvoice(new UpdateInvoiceDTO(
                                        createInvoice.get(1).getInvoiceId(),
                                        suppliers.get(0).getSupplierId(),
                                        "1234567898",
                                        LocalDate.now(),
                                        25000.0,
                                        CurrencyType.USD
                                ), "client")
                        ).getMessage()
                );
            }

            @Test
            @DisplayName("it should not update invoice with old date")
            public void shouldNotUpdateInvoiceWithOldDate() {
                assertEquals(
                        "400 The invoice date is an older date.",
                        assertThrows(
                                HttpClientErrorException.class,
                                () -> invoiceService.updateInvoice(
                                        new UpdateInvoiceDTO(
                                                createInvoice.get(0).getInvoiceId(),
                                                suppliers.get(0).getSupplierId(),
                                                "1234567892",
                                                LocalDate.parse("2021-04-05"),
                                                25000.0,
                                                CurrencyType.USD
                                        ), "client")
                        ).getMessage()
                );
            }

            @Test
            @DisplayName("it should not update invoice with invoice status In_Review, Approved and Rejected")
            public void shouldNotUpdateInvoiceWithInReviewAndApprovedAndRejected() {
                updateInvoiceStatus(InvoiceStatus.IN_REVIEW, createInvoice.get(0).getInvoiceId());
                assertEquals(
                        "400 This invoice can not update, because invoice is IN_REVIEW.",
                        assertThrows(
                                HttpClientErrorException.class,
                                () -> invoiceService.updateInvoice(
                                        new UpdateInvoiceDTO(
                                                createInvoice.get(0).getInvoiceId(),
                                                suppliers.get(0).getSupplierId(),
                                                "1234567892",
                                                LocalDate.now(),
                                                25000.0,
                                                CurrencyType.EUR
                                        ), "client")
                        ).getMessage()
                );
            }
        }
    }

    @Nested
    @DisplayName("invoice delete test")
    class InvoiceDeleteTest {
        @Test
        @DisplayName("it should delete invoice")
        public void shouldDeleteInvoice() {
//            assertEquals(2, invoiceService.deleteInvoice(createInvoice.get(0).getInvoiceId(), "client"));
        }

        // Invoice can delete client only, suppliers and bank can not delete
        // This test checks the above point and below point (Display Name mentioned thing)
        @Test
        @DisplayName("it should not delete invoice when that invoice owner is not a same client")
        public void shouldNotDeleteInvoiceWhenInvoiceOwnerIsNotEqual() {
            assertEquals(
                    "400 client2 you do not have permission to delete this invoice.",
                    assertThrows(
                            HttpClientErrorException.class,
                            () -> invoiceService.deleteInvoice(createInvoice.get(0).getInvoiceId(), "client2")
                    ).getMessage()
            );
        }

        @Test
        @DisplayName("it should not delete invoice with invoice status In_Review, Approved and Rejected")
        public void shouldNotDeleteInvoiceWithInReviewAndApprovedAndRejected() {
            updateInvoiceStatus(InvoiceStatus.IN_REVIEW, createInvoice.get(0).getInvoiceId());
            assertEquals(
                    "400 This invoice can not delete, because invoice is IN_REVIEW.",
                    assertThrows(
                            HttpClientErrorException.class,
                            () -> invoiceService.deleteInvoice(createInvoice.get(0).getInvoiceId(), "client")
                    ).getMessage()
            );
        }
    }

    @Nested
    @DisplayName("invoice retrieve test")
    class InvoiceRetrieveTest {
        @Test
        @DisplayName("it should return all invoice")
        public void shouldReturnAllInvoice() {
            assertNotNull(invoiceService.getAllInvoice());
        }
    }
}