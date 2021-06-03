package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.config.GlobalErrorHandler;
import com.hcl.capstoneserver.invoice.dto.CreateInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.StatusUpdateInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.UpdateInvoiceDTO;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.UserService;
import com.hcl.capstoneserver.user.UserType;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ModelMapper mapper;
    private final UserService userService;

    public InvoiceService(InvoiceRepository invoiceRepository, ModelMapper mapper, UserService userService) {
        this.invoiceRepository = invoiceRepository;
        this.mapper = mapper;
        this.userService = userService;
    }

    private void _checkInvoiceNumberExistsInSupplier(CreateInvoiceDTO dto, Supplier supplier) {
        // check the supplier has same invoice Number
        Invoice invoiceQry = new Invoice();
        invoiceQry.setInvoiceNumber(dto.getInvoiceNumber());
        invoiceQry.setSupplierId(supplier);
        if (invoiceRepository.exists(Example.of(invoiceQry))) {
            throw new HttpClientErrorException(
                    HttpStatus.FORBIDDEN,
                    "Invoice number all ready exists for this supplier"
            );
        }
    }

    private void _checkInvoiceOwnerIsClient(String invoiceNumber, String invoiceDate, Client client) {
        // check the current invoice owner is client/ supplier or not
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setInvoiceDate(invoiceDate);
        invoice.setClientId(client);
        if (invoiceRepository.exists(Example.of(invoice))) {
            throw new HttpClientErrorException(
                    HttpStatus.FORBIDDEN,
                    String.format(
                            "%s you do not have permission to update this",
                            client.getUserId()
                    )
            );
        }
    }

    private void _checkInvoiceData(
            Optional<Client> client,
            Optional<Supplier> supplier,
            String invoiceDate,
            InvoiceStatus status,
            String field
    ) {
        // check the invoice data valid or not
        if (!client.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Client not found");
        }

        if (!supplier.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Supplier not found");
        }

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

        if (LocalDate.now().isAfter(LocalDate.parse(invoiceDate))) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invoice date is expired");
        }
    }

    public Optional<Invoice> fetchInvoiceById(Integer invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }

    public List<Invoice> getUserOwnInvoices(UserType userType, String userId) {
        List<Invoice> invoices = null;
        for (Invoice invoice : getAllInvoice()) {
            switch (userType) {
                case CLIENT:
                    if (invoice.getClientId().equals(userId)) {
                        invoices.add(invoice);
                    }
                    break;
                case SUPPLIER:
                    if (invoice.getSupplierId().equals(userId)) {
                        invoices.add(invoice);
                    }
                    break;
            }
        }
        return invoices;
    }

    public Invoice createInvoice(CreateInvoiceDTO dto, String userId) {
        Optional<Client> client = userService.fetchClientDataByUserId(userId);
        Optional<Supplier> supplier = userService.fetchSupplierDataByUserId(dto.getSupplierId());
        _checkInvoiceData(client, supplier, dto.getInvoiceDate(), dto.getStatus(), "create");
        _checkInvoiceNumberExistsInSupplier(dto, supplier.get());

        return mapper.map(invoiceRepository.save(
                new Invoice(
                        client.get(),
                        supplier.get(),
                        dto.getInvoiceNumber(),
                        dto.getInvoiceDate(),
                        dto.getAmount(),
                        dto.getStatus(),
                        dto.getCurrencyType()
                )
        ), Invoice.class);
    }

    //Client invoice update
    public Invoice updateInvoice(UpdateInvoiceDTO dto, String userId) {
        Optional<Client> client = userService.fetchClientDataByUserId(userId);
        Optional<Supplier> supplier = userService.fetchSupplierDataByUserId(dto.getSupplierId());
        Optional<Invoice> invoice = fetchInvoiceById(dto.getInvoiceId());

        _checkInvoiceData(client, supplier, dto.getInvoiceDate(), invoice.get().getStatus(), "update");

        if (!invoice.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invoice not found");
        }

        _checkInvoiceOwnerIsClient(dto.getInvoiceNumber(), dto.getInvoiceDate(), client.get());

        invoice.get().setSupplierId(supplier.get());
        invoice.get().setInvoiceNumber(dto.getInvoiceNumber());
        invoice.get().setInvoiceDate(dto.getInvoiceDate());
        invoice.get().setAmount(dto.getAmount());
        invoice.get().setCurrencyType(dto.getCurrencyType());
        return mapper.map(invoiceRepository.save(invoice.get()), Invoice.class);
    }

    // This function use Bank for get all invoice
    public List<Invoice> getAllInvoice() {
        return invoiceRepository.findAll();
    }

    // This function use Client for get his/ her all invoice
    public List<Invoice> getClientAllInvoice(String userId) {
        Optional<Client> client = userService.fetchClientDataByUserId(userId);
        List<Invoice> invoices = getUserOwnInvoices(UserType.CLIENT, client.get().getClientId());
        return invoices;
    }

    // This function use Supplier for get his/ her all invoice
    public List<Invoice> getSupplierAllInvoice(String userId) {
        Optional<Supplier> supplier = userService.fetchSupplierDataByUserId(userId);
        List<Invoice> invoices = getUserOwnInvoices(UserType.SUPPLIER, supplier.get().getSupplierId());
        return invoices;
    }

    // Bank invoice status update
    public Invoice setStatus(StatusUpdateInvoiceDTO dto) {
        return mapper.map(invoiceRepository.save(
                new Invoice(
                        dto.getInvoiceId(),
                        dto.getStatus()
                )
        ), Invoice.class);
    }

    public void deleteInvoice(Integer invoiceId, String userId) {
        Optional<Client> client = userService.fetchClientDataByUserId(userId);
        Optional<Invoice> invoice = invoiceRepository.findById(invoiceId);
        _checkInvoiceData(client, null, null, invoice.get().getStatus(), "delete");
        _checkInvoiceOwnerIsClient(invoice.get().getInvoiceNumber(), invoice.get().getInvoiceDate(), client.get());
        invoiceRepository.deleteById(invoiceId);
    }
}
