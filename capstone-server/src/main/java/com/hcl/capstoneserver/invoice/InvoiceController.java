package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.InvoiceDTO;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin
@RestController
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final ModelMapper mapper;

    public InvoiceController(InvoiceService invoiceService, ModelMapper mapper) {
        this.invoiceService = invoiceService;
        this.mapper = mapper;
    }

    @PostMapping("/api/invoices/create")
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceDTO dto, Principal principal) {
        return new ResponseEntity<>(invoiceService.createInvoice(dto, principal.getName()), HttpStatus.CREATED);
    }
}
