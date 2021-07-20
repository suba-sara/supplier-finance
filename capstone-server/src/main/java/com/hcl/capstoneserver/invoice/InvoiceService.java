package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.file.UploadedFileService;
import com.hcl.capstoneserver.file.entities.UploadedFile;
import com.hcl.capstoneserver.invoice.dto.*;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import com.hcl.capstoneserver.invoice.exception.*;
import com.hcl.capstoneserver.invoice.repositories.InvoiceCriteriaRepository;
import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.UserService;
import com.hcl.capstoneserver.user.UserType;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ModelMapper mapper;
    private final UserService userService;
    private final InvoiceCriteriaRepository invoiceCriteriaRepository;
    private final UploadedFileService uploadedFileService;

    /*
     * userId - current login user userId
     * */

    public InvoiceService(
            InvoiceRepository invoiceRepository,
            ModelMapper mapper,
            UserService userService,
            InvoiceCriteriaRepository invoiceCriteriaRepository,
            UploadedFileService uploadedFileService
    ) {
        this.invoiceRepository = invoiceRepository;
        this.mapper = mapper;
        this.userService = userService;
        this.invoiceCriteriaRepository = invoiceCriteriaRepository;
        this.uploadedFileService = uploadedFileService;

        // model mapper set to ignore the null values
        this.mapper.getConfiguration().setSkipNullEnabled(true);
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

    private Page<Invoice> _getInvoice(InvoiceSearchCriteriaDTO dto) {
        return invoiceCriteriaRepository.findAllWithFilters(dto);
    }

    public InvoiceCreatedDTO createInvoice(CreateInvoiceDTO dto, String userId) {
        Client client = userService.fetchClientDataByUserId(userId);
        Supplier supplier = userService.fetchSupplierDataBySupplierId(dto.getSupplierId());

        _checkSupplierWithExistsInvoiceNumber(supplier, dto.getInvoiceNumber());
        _checkInvoiceDate(dto.getInvoiceDate(), UserType.CLIENT);

        //create invoice
        Invoice invoice = new Invoice(
                client,
                supplier,
                dto.getInvoiceNumber(),
                LocalDate.now(),
                dto.getInvoiceDate(),
                dto.getAmount(),
                InvoiceStatus.UPLOADED,
                dto.getCurrencyType()
        );

        //create invoice
        invoiceRepository.save(invoice);

        //create invoice file
        UploadedFile initialFile = uploadedFileService.createInitialFile(String.format(
                "invoice-%s-%s",
                invoice.getInvoiceId(),
                invoice.getInvoiceNumber()
        ));


        return new InvoiceCreatedDTO(
                initialFile.getId(),
                initialFile.getToken(),
                mapper.map(invoice, ClientViewInvoiceDTO.class)
        );
    }

    // This update method for client
    public ClientViewInvoiceDTO updateInvoice(UpdateInvoiceDTO dto, String userId) {
        Invoice invoice = _checkInvoiceOwnershipAndFetchInvoice(userId, dto.getInvoiceId(), "update");
        Supplier supplier = invoice.getSupplier();
        String invoiceNumber = invoice.getInvoiceNumber();
        if (Objects.nonNull(dto.getSupplierId())) {
            supplier = userService.fetchSupplierDataBySupplierId(dto.getSupplierId());
        }

        if (Objects.nonNull(dto.getInvoiceNumber())) {
            invoiceNumber = dto.getInvoiceNumber();
        }

        if (Objects.nonNull(dto.getInvoiceDate())) {
            _checkInvoiceDate(dto.getInvoiceDate(), UserType.CLIENT);
        }

        _checkSupplierWithExistsInvoiceNumber(supplier, invoiceNumber);
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
    public Page<BankViewInvoiceDTO> getBankInvoice(InvoiceSearchCriteriaDTO dto, String userId) {
        // need to check userId account type -> This feature currently unavailable
        // One feature needs to be check when BANK user is created: invoice status can update only by BANK
        return _getInvoice(dto).map(invoice -> mapper.map(invoice, BankViewInvoiceDTO.class));
    }

    // This function use Client for get his/ her all invoice
    public Page<ClientViewInvoiceDTO> getClientInvoice(InvoiceSearchCriteriaDTO dto, String userId) {
        dto.setClientId(userService.getClientId(userId));
        return _getInvoice(dto).map(invoice -> mapper.map(invoice, ClientViewInvoiceDTO.class));
    }

    // This function use Supplier for get his/ her all invoice
    public Page<SupplierVIewInvoiceDTO> getSupplierInvoice(InvoiceSearchCriteriaDTO dto, String userId) {
        dto.setSupplierId(userService.getSupplierId(userId));
        return _getInvoice(dto).map(invoice -> mapper.map(invoice, SupplierVIewInvoiceDTO.class));
    }
}