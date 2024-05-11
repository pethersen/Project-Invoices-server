package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/invoices")
    public InvoiceDTO addInvoice(@RequestBody InvoiceDTO invoiceDTO) {

        return invoiceService.addInvoice(invoiceDTO);
    }

    @GetMapping("/invoices")
    public List<InvoiceDTO> getInvoices(InvoiceFilter invoiceFilter) {
        return invoiceService.getAll(invoiceFilter);
    }

    @GetMapping("/identification/{identificationNumber}/sales")
    public List<InvoiceDTO> getInvoicesBySeller(@PathVariable String identificationNumber) {
        return invoiceService.getAllBySeller(identificationNumber);
    }

    @GetMapping("/identification/{identificationNumber}/purchases")
    public List<InvoiceDTO> getInvoicesByBuyer(@PathVariable String identificationNumber) {
        return invoiceService.getAllByBuyer(identificationNumber);
    }

    @GetMapping("/invoices/{id}")
    public InvoiceDTO getInvoiceDetail(@PathVariable Long id) {
        return invoiceService.getById(id);
    }

    @PutMapping("/invoices/{id}")
    public InvoiceDTO editInvoice(@PathVariable Long id, @RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.editInvoice(id, invoiceDTO);
    }

    @DeleteMapping("/invoices/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable Long id) { invoiceService.removeInvoice(id);
    }

    @GetMapping("/invoices/statistics")
    public InvoiceStatisticsDTO getInvoiceStatistics() {
        return invoiceService.getStatistics();
    }
}
