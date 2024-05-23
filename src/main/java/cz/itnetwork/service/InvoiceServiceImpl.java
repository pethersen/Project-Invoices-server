package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PersonRepository personRepository;

    /**
     * Adds a new invoice to the database.
     *
     * @param invoiceDTO The DTO representing the invoice to be added.
     * @return The DTO representing the added invoice.
     */
    @Override
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
        InvoiceEntity newInvoiceEntity = invoiceMapper.toEntity(invoiceDTO);
        mapPeopleToInvoice(newInvoiceEntity, invoiceDTO);
        newInvoiceEntity = invoiceRepository.saveAndFlush(newInvoiceEntity);
        return invoiceMapper.toDTO(newInvoiceEntity);
    }

    /**
     * Retrieves all invoices based on the provided filter.
     *
     * @param invoiceFilter The filter criteria for fetching invoices.
     * @return A list of DTOs representing the filtered invoices.
     */
    @Override
    public List<InvoiceDTO> getAll(InvoiceFilter invoiceFilter) {
        InvoiceSpecification invoiceSpecification = new InvoiceSpecification(invoiceFilter);
        return invoiceRepository.findAll(invoiceSpecification, PageRequest.of(0, invoiceFilter.getLimit()))
                .stream()
                .map(invoiceMapper::toDTO)
                .toList();
    }

    /**
     * Retrieves all invoices associated with a seller based on the seller's identification number.
     *
     * @param identificationNumber The identification number of the seller.
     * @return A list of DTOs representing the invoices associated with the seller.
     */
    @Override
    public List<InvoiceDTO> getAllBySeller(String identificationNumber) {
        return invoiceRepository.findBySeller_IdentificationNumber(identificationNumber)
                .stream()
                .map(i -> invoiceMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all invoices associated with a buyer based on the buyer's identification number.
     *
     * @param identificationNumber The identification number of the buyer.
     * @return A list of DTOs representing the invoices associated with the buyer.
     */
    @Override
    public List<InvoiceDTO> getAllByBuyer(String identificationNumber) {
        return invoiceRepository.findByBuyer_IdentificationNumber(identificationNumber)
                .stream()
                .map(i -> invoiceMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an invoice by its unique identifier.
     *
     * @param id The unique identifier of the invoice.
     * @return The DTO representing the retrieved invoice.
     * @throws NotFoundException if the invoice with the specified ID is not found in the database.
     */
    @Override
    public InvoiceDTO getById(long id) {
        InvoiceEntity invoiceEntity = fetchInvoiceById(id);
        return invoiceMapper.toDTO(invoiceEntity);
    }

    /**
     * Edits an existing invoice.
     *
     * @param id         The unique identifier of the invoice to be edited.
     * @param invoiceDTO The DTO representing the updated invoice information.
     * @return The DTO representing the edited invoice.
     */
    @Override
    public InvoiceDTO editInvoice(long id, InvoiceDTO invoiceDTO) {
        try {
            InvoiceEntity invoiceEntity = invoiceMapper.toEntity(invoiceDTO);
            invoiceEntity.setId(id);
            InvoiceEntity saved = invoiceRepository.save(invoiceEntity);
            return invoiceMapper.toDTO(saved);
        } catch (NotFoundException ignored) {
            // The contract in the interface states that no exception is thrown if the entity is not found.
        }
        return null;
    }

    /**
     * Removes an invoice from the database.
     *
     * @param id The unique identifier of the invoice to be removed.
     */
    @Override
    public void removeInvoice(long id) {
        try {
            InvoiceEntity invoiceEntity = fetchInvoiceById(id);
            invoiceRepository.delete(invoiceEntity);
        } catch (NotFoundException ignored) {
            // The contract in the interface states that no exception is thrown if the entity is not found.
        }
    }

    /**
     * Fetches an invoice entity by its unique identifier.
     *
     * @param id The unique identifier of the invoice.
     * @return The fetched invoice entity.
     * @throws NotFoundException if the invoice with the specified ID is not found in the database.
     */
    private InvoiceEntity fetchInvoiceById(long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice with id " + id + " wasn't found in the database."));
    }

    /**
     * Retrieves statistics related to invoices.
     *
     * @return A DTO containing invoice statistics.
     */
    public InvoiceStatisticsDTO getStatistics() {
        InvoiceStatisticsDTO invoiceStatisticsDTO = new InvoiceStatisticsDTO();
        invoiceStatisticsDTO.setCurrentYearSum(invoiceRepository.getCurrentYearSum());
        invoiceStatisticsDTO.setAllTimeSum(invoiceRepository.getAllTimeSum());
        invoiceStatisticsDTO.setInvoicesCount(invoiceRepository.getInvoiceCount());
        return invoiceStatisticsDTO;
    }

    /**
     * Maps buyer and seller entities to the invoice entity.
     *
     * @param invoiceEntity The invoice entity to which people are mapped.
     * @param invoiceDTO    The DTO containing buyer and seller information.
     */
    private void mapPeopleToInvoice(InvoiceEntity invoiceEntity, InvoiceDTO invoiceDTO) {
        invoiceEntity.setBuyer(personRepository.getReferenceById(invoiceDTO.getBuyer().getId()));
        invoiceEntity.setSeller(personRepository.getReferenceById(invoiceDTO.getSeller().getId()));
    }
}
