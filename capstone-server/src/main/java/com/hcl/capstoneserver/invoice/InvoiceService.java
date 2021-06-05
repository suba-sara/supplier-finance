package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.*;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.UserService;
import com.hcl.capstoneserver.user.UserType;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ModelMapper mapper;
    private final UserService userService;

    /*
     * userId - current login user userId
     * */

    public InvoiceService(
            InvoiceRepository invoiceRepository,
            ModelMapper mapper,
            UserService userService
    ) {
        this.invoiceRepository = invoiceRepository;
        this.mapper = mapper;
        this.userService = userService;
    }

    private void _checkSupplierWithExistsInvoiceNumber(Optional<Supplier> supplier, String invoiceNumber) {
        Invoice invoice = new Invoice();
        invoice.setSupplier(supplier.get());
        invoice.setInvoiceNumber(invoiceNumber);
        if (invoiceRepository.exists(Example.of(invoice))) {
            throw new HttpClientErrorException(
                    HttpStatus.BAD_REQUEST,
                    "An invoice number already exists for this supplier."
            );
        }
    }

    private void _checkInvoiceDate(LocalDate invoiceDate) {
        if (LocalDate.now().isAfter(invoiceDate)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "The invoice date is an older date.");
        }
    }

    private Optional<Invoice> _fetchInvoiceById(Integer invoiceId) {
        Optional<Invoice> invoice = invoiceRepository.findById(invoiceId);
        if (!invoice.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invoice is not found.");
        }
        return invoice;
    }

    private Optional<Invoice> _checkInvoiceOwnershipAndFetchInvoice(
            String userId,
            Integer invoiceId,
            String field
    ) {
        Optional<Invoice> invoice = _fetchInvoiceById(invoiceId);
        InvoiceStatus status = invoice.get().getStatus();
        switch (status) {
            case IN_REVIEW:
            case APPROVED:
                throw new HttpClientErrorException(
                        HttpStatus.BAD_REQUEST,
                        String.format(
                                "This invoice can not %s, because invoice is %s.",
                                field, status.toString().toLowerCase()
                        )
                );
        }
        if (!invoice.get().getClient().getUserId().equals(userId)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format(
                    "%s you do not have permission to %s this invoice.",
                    userId, field
            )
            );
        }
        return invoice;
    }

    private Invoice _fetchAllInvoiceByInvoiceStatus(InvoiceStatus status, Integer invoiceId) {
        return invoiceRepository.findAll((Invoice, cq, cb) -> {
                                             cb.equal(Invoice.get("status"), status);
                                             cb.equal(Invoice.get("invoiceId"), invoiceId);
                                             return null;
                                         }
        ).get(0);
    }

    public List<Invoice> _getUserOwnAllInvoices(UserType userType, String userId) {
        List<Invoice> invoices = null;
        for (Invoice invoice : getAllInvoice()) {
            switch (userType) {
                case CLIENT:
                    if (invoice.getClient().equals(userId)) {
                        invoices.add(invoice);
                    }
                    break;
                case SUPPLIER:
                    if (invoice.getSupplier().equals(userId)) {
                        invoices.add(invoice);
                    }
                    break;
            }
        }
        return invoices;
    }

    public ClientViewInvoiceDTO createInvoice(CreateInvoiceDTO dto, String userId) {
        Optional<Client> client = userService.fetchClientDataByUserId(userId);
        Optional<Supplier> supplier = userService.fetchSupplierDataBySupplierId(dto.getSupplierId());
        if (!supplier.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "This Supplier is not found.");
        }
        _checkSupplierWithExistsInvoiceNumber(supplier, dto.getInvoiceNumber());
        _checkInvoiceDate(dto.getInvoiceDate());

        return mapper.map(invoiceRepository.save(new Invoice(
                client.get(),
                supplier.get(),
                dto.getInvoiceNumber(),
                dto.getInvoiceDate(),
                dto.getAmount(),
                dto.getStatus(),
                dto.getCurrencyType()
        )), ClientViewInvoiceDTO.class);
    }

    // This update method for client
    public ClientViewInvoiceDTO updateInvoice(UpdateInvoiceDTO dto, String userId) {
        Optional<Client> client = userService.fetchClientDataByUserId(userId);
        Optional<Supplier> supplier = userService.fetchSupplierDataBySupplierId(dto.getSupplierId());

        if (!supplier.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "This Supplier is not found");
        }

        Optional<Invoice> invoice = _checkInvoiceOwnershipAndFetchInvoice(userId, dto.getInvoiceId(), "update");

        _checkSupplierWithExistsInvoiceNumber(supplier, dto.getInvoiceNumber());
        _checkInvoiceDate(dto.getInvoiceDate());

        mapper.map(dto, invoice.get());
        return mapper.map(invoiceRepository.save(invoice.get()), ClientViewInvoiceDTO.class);
    }

    // This method use only Bank
    public BankViewInvoiceDTO statusUpdate(StatusUpdateInvoiceDTO dto, String userId) {
        // need to check userId account type -> This feature currently unavailable
        Optional<Invoice> invoice = _fetchInvoiceById(dto.getInvoiceId());
        mapper.map(dto, invoice.get());
        return mapper.map(invoiceRepository.save(invoice.get()), BankViewInvoiceDTO.class);
    }

    public long deleteInvoice(Integer invoiceId, String userId) {

        invoiceRepository.delete(_checkInvoiceOwnershipAndFetchInvoice(userId, invoiceId, "delete").get());
        return invoiceRepository.count();
    }

    // This function used by BANK
    public List<Invoice> fetchAllInvoices() {
        return invoiceRepository.findAll();
    }


    // This function use Bank for get all invoice
    public List<Invoice> getAllInvoice() {
        return invoiceRepository.findAll();
    }

    // This function use Client for get his/ her all invoice
    public List<Invoice> getClientAllInvoice(String userId) {
        Optional<Client> client = userService.fetchClientDataByUserId(userId);
        List<Invoice> invoices = _getUserOwnAllInvoices(UserType.CLIENT, client.get().getClientId());
        return invoices;
    }


    // This function use Supplier for get his/ her all invoice
    public List<Invoice> getSupplierAllInvoice(String userId) {
        Optional<Supplier> supplier = userService.fetchSupplierDataByUserId(userId);
        List<Invoice> invoices = _getUserOwnAllInvoices(UserType.SUPPLIER, supplier.get().getSupplierId());
        return invoices;
    }

    // This method can use client and supplier for get their invoice filter by status
    public List<Invoice> getUserAllInvoiceByStatus(String userId, InvoiceStatus status) {
        List<Invoice> invoiceAll = getClientAllInvoice(userId);
        List<Invoice> invoicesFilter = new ArrayList<>();
        for (Invoice invoice : invoiceAll) {
            invoicesFilter.add(_fetchAllInvoiceByInvoiceStatus(status, invoiceAll.get(0).getInvoiceId()));
        }
        return invoicesFilter;
    }
}