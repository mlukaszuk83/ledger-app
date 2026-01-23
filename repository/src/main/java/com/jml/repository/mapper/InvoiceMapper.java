package com.jml.repository.mapper;

import com.jml.domain.dto.InvoiceDTO;
import com.jml.repository.model.Invoice;
import org.mapstruct.Mapper;

/**
 * @author mlukaszuk on 18.04.2022
 */
@Mapper(componentModel = "spring")
public interface InvoiceMapper {

	InvoiceDTO toDTO(Invoice invoice);

/*	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdOn", ignore = true)
	@Mapping(target = "updatedOn", ignore = true)*/
	Invoice fromDTO(InvoiceDTO invoiceDTO);
}
