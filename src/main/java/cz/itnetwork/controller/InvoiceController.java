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

    /**
     * Service class for performing operations related to invoices.
     */
    @Autowired
    private InvoiceService invoiceService;

    /**
     * Adds a new invoice.
     *
     * @param invoiceDTO The invoice data to be added.
     * @return The added invoice data.
     */
    @PostMapping("/invoices")
    public InvoiceDTO addInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.addInvoice(invoiceDTO);
    }

    /**
     * Retrieves a list of invoices based on provided filtering criteria.
     *
     * @param invoiceFilter The filter criteria for retrieving invoices.
     * @return A list of invoices filtered based on the provided criteria.
     */
    @GetMapping("/invoices")
    public List<InvoiceDTO> getInvoices(InvoiceFilter invoiceFilter) {
        return invoiceService.getAll(invoiceFilter);
    }

    /**
     * Retrieves a list of invoices associated with a seller identified by the identification number.
     *
     * @param identificationNumber The identification number of the seller.
     * @return A list of invoices associated with the specified seller.
     */
    @GetMapping("/identification/{identificationNumber}/sales")
    public List<InvoiceDTO> getInvoicesBySeller(@PathVariable String identificationNumber) {
        return invoiceService.getAllBySeller(identificationNumber);
    }

    /**
     * Retrieves a list of invoices associated with a buyer identified by the identification number.
     *
     * @param identificationNumber The identification number of the buyer.
     * @return A list of invoices associated with the specified buyer.
     */
    @GetMapping("/identification/{identificationNumber}/purchases")
    public List<InvoiceDTO> getInvoicesByBuyer(@PathVariable String identificationNumber) {
        return invoiceService.getAllByBuyer(identificationNumber);
    }

    /**
     * Retrieves details of a specific invoice identified by its ID.
     *
     * @param id The ID of the invoice.
     * @return Details of the invoice identified by the provided ID.
     */
    @GetMapping("/invoices/{id}")
    public InvoiceDTO getInvoiceDetail(@PathVariable Long id) {
        return invoiceService.getById(id);
    }

    /**
     * Edits an existing invoice identified by its ID.
     *
     * @param id         The ID of the invoice to be edited.
     * @param invoiceDTO The updated invoice data.
     * @return The edited invoice data.
     */
    @PutMapping("/invoices/{id}")
    public InvoiceDTO editInvoice(@PathVariable Long id, @RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.editInvoice(id, invoiceDTO);
    }

    /**
     * Deletes an existing invoice identified by its ID.
     *
     * @param id The ID of the invoice to be deleted.
     */
    @DeleteMapping("/invoices/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.removeInvoice(id);
    }

    /**
     * Retrieves statistics related to invoices.
     *
     * @return Statistics related to invoices.
     */
    @GetMapping("/invoices/statistics")
    public InvoiceStatisticsDTO getInvoiceStatistics() {
        return invoiceService.getStatistics();
    }
}
