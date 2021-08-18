package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.CreateInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.InvoiceCreatedDTO;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import com.hcl.capstoneserver.invoice.repositories.InvoiceRepository;
import com.hcl.capstoneserver.user.UserService;
import com.hcl.capstoneserver.user.dto.ClientDTO;
import com.hcl.capstoneserver.user.dto.SupplierDTO;
import com.hcl.capstoneserver.user.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceTestUtils {
    @Autowired
    InvoiceService invoiceService;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    UserService userService;

    @Autowired
    ClientRepository clientRepository;

    public List<InvoiceCreatedDTO> createInvoice(List<SupplierDTO> supplier) {
        List<InvoiceCreatedDTO> dtoList = new ArrayList<>();
        dtoList.add(invoiceService.createInvoice(new CreateInvoiceDTO(
                supplier.get(0).getSupplierId(),
                "1234567898",
                LocalDate.now(),
                25000.0,
                CurrencyType.USD
        ), "client"));
        dtoList.add(invoiceService.createInvoice(new CreateInvoiceDTO(
                supplier.get(1).getSupplierId(),
                "1234567899",
                LocalDate.now(),
                25000.0,
                CurrencyType.USD
        ), "client2"));


        return dtoList;
    }

    public Invoice createExpiredInvoice(List<SupplierDTO> supplier, List<ClientDTO> client) {
        return invoiceRepository.save(new Invoice(
                userService.fetchClientDataByClientId(client.get(0).getClientId()),
                userService.fetchSupplierDataBySupplierId(supplier.get(0).getSupplierId()),
                "999999999",
                LocalDate.parse("2021-04-01"),
                LocalDate.parse("2021-05-01"),
                40000.0,
                InvoiceStatus.IN_REVIEW,
                CurrencyType.GBP
        ));
    }
}
