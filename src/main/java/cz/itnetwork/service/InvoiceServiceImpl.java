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

    @Override
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
        InvoiceEntity newInvoiceEntity = invoiceMapper.toEntity(invoiceDTO);
        mapPeopleToInvoice(newInvoiceEntity, invoiceDTO);
        newInvoiceEntity = invoiceRepository.saveAndFlush(newInvoiceEntity);

        return invoiceMapper.toDTO(newInvoiceEntity);
    }

    @Override
    public List<InvoiceDTO> getAll(InvoiceFilter invoiceFilter) {
        InvoiceSpecification invoiceSpecification = new InvoiceSpecification(invoiceFilter);

        return invoiceRepository.findAll(invoiceSpecification, PageRequest.of(0, invoiceFilter.getLimit()))
                .stream()
                .map(invoiceMapper :: toDTO)
                .toList();
    }

    private void mapPeopleToInvoice(InvoiceEntity invoiceEntity, InvoiceDTO invoiceDTO){
        invoiceEntity.setBuyer(personRepository.getReferenceById(invoiceDTO.getBuyer().getId()));
        invoiceEntity.setSeller(personRepository.getReferenceById(invoiceDTO.getSeller().getId()));
    }

    @Override
    public List<InvoiceDTO> getAllBySeller(String identificationNumber) {
        return invoiceRepository.findBySeller_IdentificationNumber(identificationNumber)
                .stream()
                .map(i -> invoiceMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> getAllByBuyer(String identificationNumber) {
                return invoiceRepository.findByBuyer_IdentificationNumber(identificationNumber)
                .stream()
                .map(i -> invoiceMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO getById(long id) {
        InvoiceEntity invoiceEntity = fetchInvoiceById(id);
        return invoiceMapper.toDTO(invoiceEntity);
    }

    @Override
    public InvoiceDTO editInvoice(long id, InvoiceDTO invoiceDTO) {
        try {
            InvoiceEntity invoiceEntity = invoiceMapper.toEntity(invoiceDTO);
            invoiceEntity.setId(id);
            InvoiceEntity saved = invoiceRepository.save(invoiceEntity);

            return invoiceMapper.toDTO(saved);

        } catch (NotFoundException ignored) {
            // The contract in the interface states, that no exception is thrown, if the entity is not found.
        }
        return null;
    }

    @Override
    public void removeInvoice(long id) {
        try {
            InvoiceEntity invoiceEntity = fetchInvoiceById(id);
            invoiceRepository.delete(invoiceEntity);

        } catch (NotFoundException ignored) {
            // The contract in the interface states, that no exception is thrown, if the entity is not found.
        }
    }

    private InvoiceEntity fetchInvoiceById(long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice with id " + id + " wasn't found in the database."));
    }

    public InvoiceStatisticsDTO getStatistics() {
        InvoiceStatisticsDTO invoiceStatisticsDTO = new InvoiceStatisticsDTO();
        invoiceStatisticsDTO.setCurrentYearSum(invoiceRepository.getCurrentYearSum());
        invoiceStatisticsDTO.setAllTimeSum(invoiceRepository.getAllTimeSum());
        invoiceStatisticsDTO.setInvoicesCount(invoiceRepository.getInvoiceCount());
        return invoiceStatisticsDTO;
    }
}
