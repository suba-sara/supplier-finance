package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.InvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.StatusUpdateInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.UpdateInvoiceDTO;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import com.hcl.capstoneserver.invoice.exceptions.InvoiceNumberExistsException;
import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.UserService;
import com.hcl.capstoneserver.user.entities.Client;
import com.hcl.capstoneserver.user.entities.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
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

    public Invoice createInvoice(InvoiceDTO invoice, String userId) {
        try {
            Optional<Client> client = userService.fetchClientIdByUserId(userId);
            Optional<Supplier> supplier = userService.fetchSupplierIdByUserId(invoice.getSupplierId());

            if (!supplier.isPresent()) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Supplier not found");
            }

            return mapper.map(invoiceRepository.save(
                    new Invoice(
                            client.get(),
                            supplier.get(),
                            invoice.getInvoiceNumber(),
                            invoice.getInvoiceDate(),
                            invoice.getAmount(),
                            invoice.getStatus(),
                            invoice.getCurrencyType()
                    )
            ), Invoice.class);
        } catch (DataIntegrityViolationException e) {
            throw new InvoiceNumberExistsException(invoice.getInvoiceNumber());
        }
    }

    // This function use Bank for get all invoice
    public List<Invoice> getAllInvoice() {
        return invoiceRepository.findAll();
    }

    //Client invoice update
    public Invoice updateInvoice(UpdateInvoiceDTO invoice, String userId) {
        try {
            Optional<Client> client = userService.fetchClientIdByUserId(userId);
            Optional<Supplier> supplier = userService.fetchSupplierIdByUserId(invoice.getSupplierId());

            if (!supplier.isPresent()) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Supplier not found");
            }

            return mapper.map(invoiceRepository.save(
                    new Invoice(
                            client.get(),
                            supplier.get(),
                            invoice.getInvoiceNumber(),
                            invoice.getInvoiceDate(),
                            invoice.getAmount(),
                            invoice.getCurrencyType()
                    )
            ), Invoice.class);
        } catch (DataIntegrityViolationException e) {
            throw new InvoiceNumberExistsException(invoice.getInvoiceNumber());
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
}
