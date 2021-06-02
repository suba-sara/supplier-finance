package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.CreateInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.StatusUpdateInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.UpdateInvoiceDTO;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import com.hcl.capstoneserver.invoice.exceptions.InvoiceNumberExistsException;
import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.UserService;
import com.hcl.capstoneserver.user.UserType;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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

    private void _checkInvoiceNumberExistsInSupplier(CreateInvoiceDTO dto, Optional<Supplier> supplier) {
        // check the supplier has same invoice Number
        Invoice invoiceQry = new Invoice();
        invoiceQry.setInvoiceNumber(dto.getInvoiceNumber());
        invoiceQry.setSupplierId(supplier.get());
        if (invoiceRepository.exists(Example.of(invoiceQry))) {
            throw new HttpClientErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Invoice number all ready exists for this supplier"
            );
        }
    }

    public Invoice createInvoice(CreateInvoiceDTO dto, String userId) {
        Optional<Client> client = userService.fetchClientIdByUserId(userId);
        Optional<Supplier> supplier = userService.fetchSupplierIdByUserId(dto.getSupplierId());

        if (!supplier.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Supplier not found");
        }

        _checkInvoiceNumberExistsInSupplier(dto, supplier);

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


    // This function use Bank for get all invoice
    public List<Invoice> getAllInvoice() {
        return invoiceRepository.findAll();
    }

    // This function use Client for get his/ her all invoice
    public List<Invoice> getClientAllInvoice(String userId) {
        Optional<Client> client = userService.fetchClientIdByUserId(userId);
        List<Invoice> invoices = getUserOwnInvoices(UserType.CLIENT, client.get().getClientId());
        return invoices;
    }

    //Client invoice update
    public Invoice updateInvoice(UpdateInvoiceDTO dto, String userId) {
        try {
            Optional<Client> client = userService.fetchClientIdByUserId(userId);
            Optional<Supplier> supplier = userService.fetchSupplierIdByUserId(dto.getSupplierId());

            if (!supplier.isPresent()) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Supplier not found");
            }
            Optional<Invoice> invoice = fetchInvoiceById(dto.getInvoiceId());
            if (!invoice.isPresent()) {

            }
            return mapper.map(invoiceRepository.save(
                    new Invoice(
                            client.get(),
                            supplier.get(),
                            dto.getInvoiceNumber(),
                            dto.getInvoiceDate(),
                            dto.getAmount(),
                            dto.getCurrencyType()
                    )
            ), Invoice.class);
        } catch (DataIntegrityViolationException e) {
            throw new InvoiceNumberExistsException(dto.getInvoiceNumber());
        }
    }

    //Bank invoice status update
    public Invoice setStatus(StatusUpdateInvoiceDTO dto) {
        return mapper.map(invoiceRepository.save(
                new Invoice(
                        dto.getInvoiceId(),
                        dto.getStatus()
                )
        ), Invoice.class);
    }

    public void deleteInvoice(Integer invoiceId) {
        invoiceRepository.deleteById(invoiceId);
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
}
