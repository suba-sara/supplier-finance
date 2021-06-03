package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.ClientViewInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.CreateInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.StatusUpdateInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.UpdateInvoiceDTO;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.UserTestUtils;
import com.hcl.capstoneserver.user.UserType;
import com.hcl.capstoneserver.user.repositories.ClientRepository;
import com.hcl.capstoneserver.user.repositories.SupplierRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.client.HttpClientErrorException;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    @BeforeAll
    public void beforeEach() {
        invoiceRepository.deleteAll();
        supplierRepository.deleteAll();
        clientRepository.deleteAll();

        userTestUtils.createAUser(UserType.SUPPLIER); // create two Suppliers-Id: supplier, supplier2
        userTestUtils.createAUser(UserType.CLIENT); // // create two client-Id: client, client2
        createInvoice = invoiceTestUtils.createInvoice();
    }

    @Test
    @DisplayName("it should create new invoice")
    public void shouldCreateNewInvoice() {
        Assertions.assertNotNull(invoiceService.createInvoice(
                new CreateInvoiceDTO(
                        "SP_00001",
                        "1234567891",
                        LocalDate.now().toString(),
                        25000.0,
                        CurrencyType.USD
                ), "client"));
    }

    @Test
    @DisplayName("it should not create new invoice with exists invoice number")
    public void shouldNotCreateNewInvoiceWithSameInvoiceNumber() {
        Assertions.assertThrows(
                HttpClientErrorException.class, () ->
                        invoiceService.createInvoice(
                                new CreateInvoiceDTO(
                                        "SP_00001",
                                        "1234567898",
                                        LocalDate.now().toString(),
                                        25000.0,
                                        CurrencyType.USD
                                ), "client"), "400 An invoice number already exists for this supplier."
        );
    }

    @Test
    @DisplayName("it should not create new invoice with old date")
    public void shouldNotCreateNewInvoiceWithOldDate() {
        Assertions.assertEquals(
                "400 The invoice date is an older date.",
                Assertions.assertThrows(
                        HttpClientErrorException.class,
                        () -> invoiceService.createInvoice(
                                new CreateInvoiceDTO(
                                        "SP_00001",
                                        "1234567892",
                                        "2021-04-05",
                                        25000.0,
                                        CurrencyType.USD
                                ), "client")
                ).getMessage()
        );
    }

    @Test
    @DisplayName("it should not create new invoice with not exists supplier")
    public void shouldNotCreateNewInvoiceWithNotExistsSupplier() {
        Assertions.assertEquals(
                "400 This Supplier is not found.",
                Assertions.assertThrows(
                        HttpClientErrorException.class,
                        () -> invoiceService.createInvoice(
                                new CreateInvoiceDTO(
                                        "SP_00003",
                                        "1234567893",
                                        LocalDate.now().toString(),
                                        25000.0,
                                        CurrencyType.USD
                                ), "client")
                ).getMessage()
        );
    }

    @Test
    @DisplayName("it should update invoice")
    public void shouldUpdateInvoice() {
        Assertions.assertEquals("1234567894", invoiceService.updateInvoice(new UpdateInvoiceDTO(
                createInvoice.get(0).getInvoiceId(),
                "SP_00001",
                "1234567894",
                LocalDate.now().toString(),
                25000.0,
                CurrencyType.USD
        ), "client").getInvoiceNumber());
    }

    @Test
    @DisplayName("it should not update invoice when that invoice owner is not a same client")
    public void shouldNotUpdateWhenInvoiceOwnerIsNotEqual() {
        Assertions.assertEquals(
                "400 client you do not have permission to update this invoice.",
                Assertions.assertThrows(
                        HttpClientErrorException.class,
                        () -> invoiceService.updateInvoice(new UpdateInvoiceDTO(
                                createInvoice.get(1).getInvoiceId(),
                                "SP_00001",
                                "1234567898",
                                LocalDate.now().toString(),
                                25000.0,
                                CurrencyType.USD
                        ), "client")
                ).getMessage()
        );
    }

    // Bank
    //    @Test
    //    @DisplayName("it should update the invoice status")
    //    public void shouldUpdateInvoiceStatus() {
    //        Assertions.assertEquals(
    //                InvoiceStatus.IN_REVIEW,
    //                invoiceService.statusUpdate(new StatusUpdateInvoiceDTO(
    //                        createInvoice.get(0)
    //                                     .getInvoiceId(),
    //                        InvoiceStatus.IN_REVIEW
    //                ), "BANK").getStatus()
    //        );
    //    }

    @Test
    @DisplayName("it should return all invoice")
    public void shouldReturnAllInvoice() {
        Assertions.assertNotNull(invoiceService.fetchAllInvoices());
    }

    @AfterAll
    @DisplayName("it should delete invoice")
    public void shouldDeleteInvoice() {
        Assertions.assertEquals(2, invoiceService.deleteInvoice(createInvoice.get(0).getInvoiceId(), "client"));
    }
}