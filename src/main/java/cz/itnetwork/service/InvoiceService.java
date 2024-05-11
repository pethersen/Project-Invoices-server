package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.filter.InvoiceFilter;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceService {

    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);

    List<InvoiceDTO> getAll(InvoiceFilter invoiceFilter);

    List<InvoiceDTO> getAllBySeller(String identificationNumber);

    List<InvoiceDTO> getAllByBuyer(String identificationNumber);

    InvoiceDTO getById(long id);

    InvoiceDTO editInvoice(long id, InvoiceDTO invoiceDTO);

    void removeInvoice(long id);

    InvoiceStatisticsDTO getStatistics();
}
