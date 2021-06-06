package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.*;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import com.hcl.capstoneserver.invoice.exception.*;
import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.UserService;
import com.hcl.capstoneserver.user.UserType;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

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

    private void _checkSupplierWithExistsInvoiceNumber(Supplier supplier, String invoiceNumber) {
        Invoice invoice = new Invoice();
        invoice.setSupplier(supplier);
        invoice.setInvoiceNumber(invoiceNumber);
        if (invoiceRepository.exists(Example.of(invoice))) {
            throw new InvoiceNumberAlreadyExistsSupplierException();
        }
    }

    private void _checkInvoiceDate(LocalDate invoiceDate, UserType userType) {
        if (LocalDate.now().isAfter(invoiceDate)) {
            String msg = null;
            switch (userType) {
                case CLIENT:
                    msg = "The invoice date is an older date.";
                    break;
                case BANK:
                    msg = "You can not update the invoice status, because invoice is expire.";
            }
            throw new InvoiceDateOldException(msg);
        }
    }

    private Invoice _fetchInvoiceById(Integer invoiceId) {
        Optional<Invoice> invoice = invoiceRepository.findById(invoiceId);
        if (!invoice.isPresent()) {
            throw new InvoiceNotFoundException("Invoice is not found.");
        }
        return invoice.get();
    }

    private Invoice _checkInvoiceOwnershipAndFetchInvoice(
            String userId,
            Integer invoiceId,
            String field
    ) {
        Invoice invoice = _fetchInvoiceById(invoiceId);
        if (!invoice.getClient().getUserId().equals(userId)) {
            throw new InvoiceOwnershipException(String.format(
                    "%s you do not have permission to %s this invoice.",
                    userId, field
            )
            );
        }
        return invoice;
    }

    private void _checkInvoiceStatus(InvoiceStatus status, String field) {
        switch (status) {
            case IN_REVIEW:
            case APPROVED:
            case REJECTED:
                throw new InvoiceStatusException(
                        String.format(
                                "This invoice can not %s, because invoice is %s.",
                                field, status
                        )
                );
        }
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
        List<Invoice> invoiceList = new ArrayList<>();
        for (Invoice invoice : getAllInvoice()) {
            switch (userType) {
                case CLIENT:
                    if (invoice.getClient().getClientId().equals(userId)) {
                        invoiceList.add(invoice);
                    }
                    break;
                case SUPPLIER:
                    if (invoice.getSupplier().getSupplierId().equals(userId)) {
                        invoiceList.add(invoice);
                    }
                    break;
            }
        }
        return invoiceList;
    }

    public ClientViewInvoiceDTO createInvoice(CreateInvoiceDTO dto, String userId) {
        Client client = userService.fetchClientDataByUserId(userId);
        Supplier supplier = userService.fetchSupplierDataBySupplierId(dto.getSupplierId());

        _checkSupplierWithExistsInvoiceNumber(supplier, dto.getInvoiceNumber());
        _checkInvoiceDate(dto.getInvoiceDate(), UserType.CLIENT);

        return mapper.map(invoiceRepository.save(new Invoice(
                client,
                supplier,
                dto.getInvoiceNumber(),
                dto.getInvoiceDate(),
                dto.getAmount(),
                dto.getStatus(),
                dto.getCurrencyType()
        )), ClientViewInvoiceDTO.class);
    }

    // This update method for client
    public ClientViewInvoiceDTO updateInvoice(UpdateInvoiceDTO dto, String userId) {
        Supplier supplier = userService.fetchSupplierDataBySupplierId(dto.getSupplierId());

        Invoice invoice = _checkInvoiceOwnershipAndFetchInvoice(userId, dto.getInvoiceId(), "update");

        _checkSupplierWithExistsInvoiceNumber(supplier, dto.getInvoiceNumber());
        _checkInvoiceDate(dto.getInvoiceDate(), UserType.CLIENT);
        _checkInvoiceStatus(invoice.getStatus(), "update");
        mapper.map(dto, invoice);
        return mapper.map(invoiceRepository.save(invoice), ClientViewInvoiceDTO.class);
    }

    // This method use only Bank
    public BankViewInvoiceDTO statusUpdate(StatusUpdateInvoiceDTO dto, String userId) {
        // need to check userId account type -> This feature currently unavailable
        // One feature needs to be check when BANK user is created: invoice status can update only by BANK
        Invoice invoice = _fetchInvoiceById(dto.getInvoiceId());
        _checkInvoiceDate(invoice.getInvoiceDate(), UserType.BANK);
        _checkInvoiceStatus(invoice.getStatus(), "update");
        mapper.map(dto, invoice);
        return mapper.map(invoiceRepository.save(invoice), BankViewInvoiceDTO.class);
    }

    public Long deleteInvoice(Integer invoiceId, String userId) {
        Invoice invoice = _checkInvoiceOwnershipAndFetchInvoice(userId, invoiceId, "delete");
        if (invoice.getStatus() == InvoiceStatus.IN_REVIEW) {
            _checkInvoiceStatus(InvoiceStatus.IN_REVIEW, "delete");
        }
        invoiceRepository.delete(invoice);
        return invoiceRepository.count();
    }

    // This function use BANK for get all invoice
    public List<Invoice> getAllInvoice() {
        return invoiceRepository.findAll();
    }

    // This function use Client for get his/ her all invoice
    public List<Invoice> getClientAllInvoice(String userId) {
        Client client = userService.fetchClientDataByUserId(userId);
        return _getUserOwnAllInvoices(UserType.CLIENT, client.getClientId());
    }


    // This function use Supplier for get his/ her all invoice
    public List<Invoice> getSupplierAllInvoice(String userId) {
        Supplier supplier = userService.fetchSupplierDataByUserId(userId);
        return _getUserOwnAllInvoices(UserType.SUPPLIER, supplier.getSupplierId());
    }

    // This method can use client and supplier for get their invoice filter by status
    public List<Invoice> getUserAllInvoiceByStatus(String userId, InvoiceStatus status) {
        List<Invoice> invoiceAll = getClientAllInvoice(userId);
        List<Invoice> invoicesFilter = new ArrayList<>();
        for (Invoice invoice : invoiceAll) {
            invoicesFilter.add(_fetchAllInvoiceByInvoiceStatus(status, invoice.getInvoiceId()));
        }
        return invoicesFilter;
    }
}