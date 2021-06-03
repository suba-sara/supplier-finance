package com.hcl.capstoneserver.invoice;

import com.hcl.capstoneserver.invoice.dto.ClientViewInvoiceDTO;
import com.hcl.capstoneserver.invoice.dto.CreateInvoiceDTO;
import com.hcl.capstoneserver.invoice.entities.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceTestUtils {
    @Autowired
    InvoiceService invoiceService;

    public List<ClientViewInvoiceDTO> createInvoice() {
        List<ClientViewInvoiceDTO> dtoList = new ArrayList<>();
        dtoList.add(invoiceService.createInvoice(new CreateInvoiceDTO(
                "SP_00001",
                "1234567898",
                LocalDate.now().toString(),
                25000.0,
                CurrencyType.USD
        ), "client"));
        dtoList.add(invoiceService.createInvoice(new CreateInvoiceDTO(
                "SP_00002",
                "1234567899",
                LocalDate.now().toString(),
                25000.0,
                CurrencyType.USD
        ), "client2"));
        return dtoList;
    }
}
