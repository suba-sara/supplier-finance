package com.hcl.capstoneserver.invoice;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ModelMapper mapper;

    public InvoiceService(InvoiceRepository invoiceRepository, ModelMapper mapper) {
        this.invoiceRepository = invoiceRepository;
        this.mapper = mapper;
    }

    public Invoice createInvoice(InvoiceDTO invoice) {
//        if (invoiceRepository.findInvoiceByInvoiceNumber(invoice.getInvoiceNumber())) {
//            throw new InvoiceNumberNotExistsException(invoice.getInvoiceNumber());
//        }

        return mapper.map(invoiceRepository.save(
                new Invoice(
                        invoice.getClientId(),
                        invoice.getSupplierId(),
                        invoice.getInvoiceNumber(),
                        invoice.getInvoiceDate(),
                        invoice.getAmount(),
                        invoice.getStatus()
                )
        ), Invoice.class);
    }
}
