package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.*;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/api/invoices/create")
    public ResponseEntity<ClientViewInvoiceDTO> createInvoice(@RequestBody CreateInvoiceDTO dto, Principal principal) {
        return new ResponseEntity<>(invoiceService.createInvoice(dto, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/api/invoices/update")
    public ResponseEntity<ClientViewInvoiceDTO> updateInvoice(@RequestBody UpdateInvoiceDTO dto, Principal principal) {
        return new ResponseEntity<>(invoiceService.updateInvoice(dto, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/api/invoices/update/status")
    public ResponseEntity<BankViewInvoiceDTO> setStatus(@RequestBody StatusUpdateInvoiceDTO dto, Principal principal) {
        return new ResponseEntity<>(invoiceService.statusUpdate(dto, principal.getName()), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/invoices/delete/{id}")
    public ResponseEntity<Long> deleteInvoice(@PathVariable Integer id, Principal principal) {
        return new ResponseEntity<>(invoiceService.deleteInvoice(id, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/api/invoices/fetchAllInvoices")
    public List<Invoice> getAllInvoice() {
        return invoiceService.getAllInvoice();
    }

    @GetMapping("/api/invoices/fetchClientInvoices")
    public List<Invoice> getClientAllInvoice(Principal principal) {
        return invoiceService.getClientAllInvoice(principal.getName());
    }

    @GetMapping("/api/invoices/fetchSupplierInvoices")
    public List<Invoice> getSupplierAllInvoice(Principal principal) {
        return invoiceService.getSupplierAllInvoice(principal.getName());
    }

    @GetMapping("/api/invoices/fetchUserInvoiceByStatus")
    public List<Invoice> getUserAllInvoiceByStatus(@RequestBody InvoiceStatus status, Principal principal) {
        return invoiceService.getUserAllInvoiceByStatus(principal.getName(), status);
    }
}
