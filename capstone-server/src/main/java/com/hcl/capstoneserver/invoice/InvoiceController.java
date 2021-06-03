package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.CreateInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.StatusUpdateInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.UpdateInvoiceDTO;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
    public ResponseEntity<Invoice> createInvoice(@RequestBody CreateInvoiceDTO dto, Principal principal) {
        return new ResponseEntity<>(invoiceService.createInvoice(dto, principal.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/api/invoices/invoices")
    public List<Invoice> getAllInvoice() {
        return invoiceService.getAllInvoice();
    }

    @GetMapping("/api/invoices/clinetInvoices")
    public List<Invoice> getClientAllInvoice(Principal principal) {
        return invoiceService.getClientAllInvoice(principal.getName());
    }

    @GetMapping("/api/invoices/supplierInvoices")
    public List<Invoice> getSupplierAllInvoice(Principal principal) {
        return invoiceService.getSupplierAllInvoice(principal.getName());
    }

    @PutMapping("/api/invoices/update")
    public ResponseEntity<Invoice> updateInvoice(@RequestBody UpdateInvoiceDTO dto, Principal principal) {
        return new ResponseEntity<>(invoiceService.updateInvoice(dto, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/api/invoices/setStatus")
    public ResponseEntity<Invoice> setStatus(@RequestBody StatusUpdateInvoiceDTO dto) {
        return new ResponseEntity<>(invoiceService.setStatus(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/invoices/delete")
    public void deleteInvoice(@RequestBody Integer invoiceId, Principal principal) {
        invoiceService.deleteInvoice(invoiceId, principal.getName());
    }
}
