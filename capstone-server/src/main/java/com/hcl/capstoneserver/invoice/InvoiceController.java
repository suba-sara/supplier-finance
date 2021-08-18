package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin
@RestController
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/api/invoices/getById/{invoiceId}")
    public ResponseEntity<ViewInvoiceDTO> getInvoice(
            @PathVariable Integer invoiceId,
            Principal principal
    ) {
        return new ResponseEntity<>(invoiceService.getInvoice(invoiceId, principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/api/invoices/create")
    public ResponseEntity<InvoiceCreatedDTO> createInvoice(
            @Valid @RequestBody CreateInvoiceDTO dto,
            Principal principal
    ) {
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

    @PutMapping("/api/invoices/request-review")
    public ResponseEntity<ViewInvoiceDTO> requestReview(@RequestBody StatusUpdateInvoiceDTO dto, Principal principal) {
        return new ResponseEntity<>(invoiceService.requestReview(dto, principal.getName()), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/invoices/delete/{id}")
    public ResponseEntity<InvoiceDeletedDto> deleteInvoice(@PathVariable Integer id, Principal principal) {
        return new ResponseEntity<>(invoiceService.deleteInvoice(id, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/api/invoices/retrieve/bank")
    public Page<BankViewInvoiceDTO> getAllInvoice(InvoiceSearchCriteriaDTO dto, Principal principal) {
        return invoiceService.getBankInvoice(dto, principal.getName());
    }

    @GetMapping("/api/invoices/retrieve/client")
    public Page<ClientViewInvoiceDTO> getClientAllInvoice(
            InvoiceSearchCriteriaDTO dto,
            Principal principal
    ) {
        return invoiceService.getClientInvoice(dto, principal.getName());
    }

    @GetMapping("/api/invoices/retrieve/supplier")
    public Page<SupplierVIewInvoiceDTO> getSupplierAllInvoice(
            InvoiceSearchCriteriaDTO dto,
            Principal principal
    ) {
        return invoiceService.getSupplierInvoice(dto, principal.getName());
    }

    @GetMapping("/api/invoices/dashboard-data")
    public ResponseEntity<DashboardDataDto> getDashboardData(Principal principal) {
        return new ResponseEntity<>(invoiceService.getDashboardData(principal.getName()), HttpStatus.OK);
    }
}
