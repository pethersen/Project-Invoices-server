package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {


    /**
     * Converts an {@link InvoiceDTO} object to an {@link InvoiceEntity} object.
     *
     * @param source the {@link  InvoiceDTO} object to convert
     * @return the converted {@link InvoiceEntity} object
     */
    InvoiceEntity toEntity(InvoiceDTO source);

    /**
     * Converts an {@link InvoiceEntity} object to an {@link InvoiceDTO} object.
     *
     * @param source the {@link InvoiceEntity} object to convert
     * @return the converted {@link InvoiceDTO} object
     */
    InvoiceDTO toDTO(InvoiceEntity source);
}
