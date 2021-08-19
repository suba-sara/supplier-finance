package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.*;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.UserTestUtils;
import com.hcl.capstoneserver.user.dto.BankerDTO;
import com.hcl.capstoneserver.user.dto.ClientDTO;
import com.hcl.capstoneserver.user.dto.SupplierDTO;
import com.hcl.capstoneserver.user.repositories.BankerRepository;
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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

    @Autowired
    BankerRepository bankerRepository;

    List<InvoiceCreatedDTO> createInvoice; // invoiceNumber : 1234567898, 1234567899
    List<SupplierDTO> suppliers;
    List<ClientDTO> clients;
    List<BankerDTO> bankers;
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
        bankerRepository.deleteAll();
        userTestUtils.accountCreate();
        suppliers = userTestUtils.createASupplier();
        clients = userTestUtils.createAClient();
        bankers = userTestUtils.createBankers();
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
        @DisplayName("it should not create new invoice with future date")
        public void shouldNotCreateNewInvoiceWithOldDate() {
            assertEquals(
                    "The invoice date is a future date.",
                    assertThrows(
                            HttpClientErrorException.class,
                            () -> invoiceService.createInvoice(
                                    new CreateInvoiceDTO(
                                            suppliers.get(0).getSupplierId(),
                                            "1234567892",
                                            LocalDate.parse("2999-04-05"),
                                            25000.0,
                                            CurrencyType.USD
                                    ), "client")
                    ).getStatusText()
            );
        }

        @Test
        @DisplayName("it should not create new invoice with not exists supplier")
        public void shouldNotCreateNewInvoiceWithNotExistsSupplier() {
            assertEquals(
                    "This SUPPLIER is not exist.",
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
                    ).getStatusText()
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


        }

        //Client
        @Nested
        @DisplayName("invoice update test: CLIENT")
        class InvoiceUpdateClientTests {
            @Test
            @DisplayName("it should update invoice")
            public void shouldUpdateInvoice() {
                UpdateInvoiceDTO dto = new UpdateInvoiceDTO();
                dto.setInvoiceId(createInvoice.get(0).getInvoice().getInvoiceId());
                dto.setInvoiceNumber("1234567894");
                assertEquals("1234567894", invoiceService.updateInvoice(dto, "client").getInvoiceNumber());
            }

            @Test
            @DisplayName("it should not update invoice when that invoice owner is not a same client")
            public void shouldNotUpdateInvoiceWhenInvoiceOwnerIsNotEqual() {
                assertEquals(
                        "client you do not have permission to update this invoice.",
                        assertThrows(
                                HttpClientErrorException.class,
                                () -> invoiceService.updateInvoice(new UpdateInvoiceDTO(
                                        createInvoice.get(1).getInvoice().getInvoiceId(),
                                        suppliers.get(0).getSupplierId(),
                                        "1234567898",
                                        LocalDate.now(),
                                        25000.0,
                                        CurrencyType.USD
                                ), "client")
                        ).getStatusText()
                );
            }

            @Test
            @DisplayName("it should not update invoice with future date")
            public void shouldNotUpdateInvoiceWithOldDate() {
                assertEquals(
                        "The invoice date is a future date.",
                        assertThrows(
                                HttpClientErrorException.class,
                                () -> invoiceService.updateInvoice(
                                        new UpdateInvoiceDTO(
                                                createInvoice.get(0).getInvoice().getInvoiceId(),
                                                suppliers.get(0).getSupplierId(),
                                                "1234567892",
                                                LocalDate.parse("2921-04-05"),
                                                25000.0,
                                                CurrencyType.USD
                                        ), "client")
                        ).getStatusText()
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
            //            assertEquals(2, invoiceService.deleteInvoice(createInvoice.get(0).getInvoice().getInvoiceId
            //            (), "client"));
        }

        // Invoice can delete client only, suppliers and bank can not delete
        // This test checks the above point and below point (Display Name mentioned thing)
        @Test
        @DisplayName("it should not delete invoice when that invoice owner is not a same client")
        public void shouldNotDeleteInvoiceWhenInvoiceOwnerIsNotEqual() {
            assertEquals(
                    "client2 you do not have permission to delete this invoice.",
                    assertThrows(
                            HttpClientErrorException.class,
                            () -> invoiceService.deleteInvoice(
                                    createInvoice.get(0).getInvoice().getInvoiceId(),
                                    "client2"
                            )
                    ).getStatusText()
            );
        }

    }

    @Nested
    @DisplayName("invoice retrieve test")
    class InvoiceRetrieveTest {

        // BANK
        @Nested
        @DisplayName("invoice retrieve test: BANK")
        class InvoiceRetrieveBank {
            @Test
            @DisplayName("it should return all invoice")
            public void shouldReturnAllInvoice() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                assertEquals(1, invoiceService.getBankInvoice(dto, "banker1").getNumberOfElements());
            }

            @Test
            @DisplayName("it should return all invoice By clientId")
            public void shouldReturnAllInvoiceByClientId() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setClientId("CL_00001");
                invoiceService.getBankInvoice(dto, "banker1")
                              .getContent()
                              .forEach(i -> assertEquals("CL_00001", i.getClient().getClientId()));
            }

            @Test
            @DisplayName("it should return all invoice By supplierId")
            public void shouldReturnAllInvoiceBySupplierId() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setSupplierId("SP_00001");

                invoiceService.getBankInvoice(dto, "banker1")
                              .getContent()
                              .forEach(i -> assertEquals("SP_00001", i.getSupplier().getSupplierId()));

            }

            @Test
            @DisplayName("it should return all invoice By invoiceNumber")
            public void shouldReturnAllInvoiceByInvoiceNumber() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setInvoiceNumber("1234567898");

                invoiceService.getBankInvoice(dto, "banker1")
                              .getContent()
                              .forEach(i -> assertEquals("1234567898", i.getInvoiceNumber()));

            }

            @Test
            @DisplayName("it should return all invoice By dateFrom")
            public void shouldReturnAllInvoiceByDateFrom() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setDateFrom(LocalDate.parse("2021-05-01"));

                invoiceService.getBankInvoice(dto, "banker1")
                              .getContent()
                              .forEach(i -> assertEquals(LocalDate.parse("2021-05-01"), i.getInvoiceDate()));

            }

            @Test
            @DisplayName("it should return all invoice Between to date")
            public void shouldReturnAllInvoiceBetweenDateFromAndDateTo() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setDateFrom(LocalDate.parse("2021-05-01"));
                dto.setDateTo(LocalDate.now());

                invoiceService.getBankInvoice(dto, "banker1")
                              .getContent()
                              .forEach(i -> assertThat(
                                      i.getInvoiceDate()
                              ).isIn(LocalDate.parse("2021-05-01"), LocalDate.now()));

            }

            @Test
            @DisplayName("it should return all invoice By ageing")
            public void shouldReturnAllInvoiceByAgeing() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setAgeing(ChronoUnit.DAYS.between(LocalDate.parse("2021-05-01"), LocalDate.now()));

                invoiceService.getBankInvoice(dto, "banker1")
                              .getContent()
                              .forEach(i -> assertEquals(
                                      LocalDate.parse("2021-05-01"),
                                      i.getInvoiceDate()
                              ));
            }

            @Test
            @DisplayName("it should return all invoice By status")
            public void shouldReturnAllInvoiceByStatus() {
                List<InvoiceStatus> statuses = new ArrayList<>();
                statuses.add(InvoiceStatus.IN_REVIEW);
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setStatus(statuses);

                invoiceService.getBankInvoice(dto, "banker1")
                              .getContent()
                              .forEach(i -> assertEquals(
                                      InvoiceStatus.IN_REVIEW,
                                      i.getStatus()
                              ));
            }

            @Test
            @DisplayName("it should return all invoice By currencyType")
            public void shouldReturnAllInvoiceByCurrencyType() {
                List<CurrencyType> currencyTypes = new ArrayList<>();
                currencyTypes.add(CurrencyType.GBP);
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setCurrencyType(currencyTypes);

                assertEquals(1, invoiceService.getBankInvoice(dto, "banker1")
                                              .getNumberOfElements());
            }
        }

        // CLIENT
        @Nested
        @DisplayName("invoice retrieve test: CLIENT")
        class InvoiceRetrieveClient {

            private List<InvoiceStatus> _getStatusList() {
                List<InvoiceStatus> statuses = new ArrayList<>();
                statuses.add(InvoiceStatus.IN_REVIEW);
                return statuses;
            }

            @Test
            @DisplayName("it should return all invoice")
            public void shouldReturnAllInvoice() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                assertEquals(2, invoiceService.getClientInvoice(dto, "client").getNumberOfElements());
            }

            @Test
            @DisplayName("it should return all invoice By supplierId and status")
            public void shouldReturnAllInvoiceBySupplierId() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setSupplierId("SP_00001");
                dto.setStatus(_getStatusList());

                invoiceService.getClientInvoice(dto, "client")
                              .getContent()
                              .forEach(i -> assertEquals("SP_00001", i.getSupplier().getSupplierId()));

            }

            @Test
            @DisplayName("it should return all invoice By invoiceNumber")
            public void shouldReturnAllInvoiceByInvoiceNumber() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setInvoiceNumber("1234567898");
                dto.setDateFrom(LocalDate.now());

                invoiceService.getClientInvoice(dto, "client")
                              .getContent()
                              .forEach(i -> assertEquals("1234567898", i.getInvoiceNumber()));

            }

            @Test
            @DisplayName("it should return all invoice By dateFrom")
            public void shouldReturnAllInvoiceByDateFrom() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setDateFrom(LocalDate.parse("2021-05-01"));

                invoiceService.getClientInvoice(dto, "client")
                              .getContent()
                              .forEach(i -> assertEquals(LocalDate.parse("2021-05-01"), i.getInvoiceDate()));

            }

            @Test
            @DisplayName("it should return all invoice Between to date")
            public void shouldReturnAllInvoiceBetweenDateFromAndDateTo() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setDateFrom(LocalDate.parse("2021-05-01"));
                dto.setDateTo(LocalDate.now());

                invoiceService.getClientInvoice(dto, "client")
                              .getContent()
                              .forEach(i -> assertThat(
                                      i.getInvoiceDate()
                              ).isIn(LocalDate.parse("2021-05-01"), LocalDate.now()));

            }

            @Test
            @DisplayName("it should return all invoice By ageing")
            public void shouldReturnAllInvoiceByAgeing() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setAgeing(ChronoUnit.DAYS.between(LocalDate.parse("2021-05-01"), LocalDate.now()));

                invoiceService.getClientInvoice(dto, "client")
                              .getContent()
                              .forEach(i -> assertEquals(
                                      LocalDate.parse("2021-05-01"),
                                      i.getInvoiceDate()
                              ));
            }

            @Test
            @DisplayName("it should return all invoice By status")
            public void shouldReturnAllInvoiceByStatus() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setStatus(_getStatusList());

                invoiceService.getClientInvoice(dto, "client")
                              .getContent()
                              .forEach(i -> assertEquals(
                                      InvoiceStatus.IN_REVIEW,
                                      i.getStatus()
                              ));
            }

            @Test
            @DisplayName("it should return all invoice By currencyType")
            public void shouldReturnAllInvoiceByCurrencyType() {
                List<CurrencyType> currencyTypes = new ArrayList<>();
                currencyTypes.add(CurrencyType.GBP);
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setCurrencyType(currencyTypes);

                assertEquals(1, invoiceService.getClientInvoice(dto, "client")
                                              .getNumberOfElements());
            }
        }

        //SUPPLIER
        @Nested
        @DisplayName("invoice retrieve test: SUPPLIER")
        class InvoiceRetrieveSupplier {

            private List<InvoiceStatus> _getStatusList() {
                List<InvoiceStatus> statuses = new ArrayList<>();
                statuses.add(InvoiceStatus.IN_REVIEW);
                return statuses;
            }

            @Test
            @DisplayName("it should return all invoice")
            public void shouldReturnAllInvoice() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                assertEquals(1, invoiceService.getSupplierInvoice(dto, "supplier").getNumberOfElements());
            }

            @Test
            @DisplayName("it should return all invoice By clientId and supplierId")
            public void shouldReturnAllInvoiceByClientId() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setClientId("CL_00001");
                invoiceService.getSupplierInvoice(dto, "supplier")
                              .getContent()
                              .forEach(i -> assertEquals("CL_00001", i.getClient().getClientId()));
            }

            @Test
            @DisplayName("it should return all invoice By invoiceNumber")
            public void shouldReturnAllInvoiceByInvoiceNumber() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setInvoiceNumber("1234567898");
                dto.setDateFrom(LocalDate.now());

                invoiceService.getSupplierInvoice(dto, "supplier")
                              .getContent()
                              .forEach(i -> assertEquals("1234567898", i.getInvoiceNumber()));

            }

            @Test
            @DisplayName("it should return all invoice By dateFrom")
            public void shouldReturnAllInvoiceByDateFrom() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setDateFrom(LocalDate.parse("2021-05-01"));

                invoiceService.getSupplierInvoice(dto, "supplier")
                              .getContent()
                              .forEach(i -> assertEquals(LocalDate.parse("2021-05-01"), i.getInvoiceDate()));

            }

            @Test
            @DisplayName("it should return all invoice Between to date")
            public void shouldReturnAllInvoiceBetweenDateFromAndDateTo() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setDateFrom(LocalDate.parse("2021-05-01"));
                dto.setDateTo(LocalDate.now());

                invoiceService.getSupplierInvoice(dto, "supplier")
                              .getContent()
                              .forEach(i -> assertThat(
                                      i.getInvoiceDate()
                              ).isIn(LocalDate.parse("2021-05-01"), LocalDate.now()));

            }

            @Test
            @DisplayName("it should return all invoice By ageing")
            public void shouldReturnAllInvoiceByAgeing() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setAgeing(ChronoUnit.DAYS.between(LocalDate.parse("2021-05-01"), LocalDate.now()));

                invoiceService.getSupplierInvoice(dto, "supplier")
                              .getContent()
                              .forEach(i -> assertEquals(
                                      LocalDate.parse("2021-05-01"),
                                      i.getInvoiceDate()
                              ));
            }

            @Test
            @DisplayName("it should return all invoice By status")
            public void shouldReturnAllInvoiceByStatus() {
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setStatus(_getStatusList());

                invoiceService.getSupplierInvoice(dto, "supplier")
                              .getContent()
                              .forEach(i -> assertEquals(
                                      InvoiceStatus.IN_REVIEW,
                                      i.getStatus()
                              ));
            }

            @Test
            @DisplayName("it should return all invoice By currencyType")
            public void shouldReturnAllInvoiceByCurrencyType() {
                List<CurrencyType> currencyTypes = new ArrayList<>();
                currencyTypes.add(CurrencyType.GBP);
                InvoiceSearchCriteriaDTO dto = new InvoiceSearchCriteriaDTO();
                dto.setCurrencyType(currencyTypes);

                assertEquals(1, invoiceService.getSupplierInvoice(dto, "supplier")
                                              .getNumberOfElements());
            }
        }
    }

    @Nested
    @DisplayName("Dashboard data test")
    class DashboardDataTest {
        @Test()
        @DisplayName("should fetch client dashboard data")
        public void shouldFetchClientData() {
            DashboardDataDto dashboardData = invoiceService.getDashboardData(clients.get(0).getUserId());
            assertEquals(1, dashboardData.getUploadedCount());
        }

        @Test()
        @DisplayName("should fetch supplier dashboard data")
        public void shouldFetchSupplierData() {
            DashboardDataDto dashboardData = invoiceService.getDashboardData(suppliers.get(0).getUserId());
            assertEquals(0, dashboardData.getInReviewCount());
        }


        @Test()
        @DisplayName("should fetch all dashboard data as banker")
        public void shouldFetchBankerData() {
            DashboardDataDto dashboardData = invoiceService.getDashboardData(bankers.get(0).getUserId());
            assertEquals(2, dashboardData.getUploadedCount());
        }
    }
}