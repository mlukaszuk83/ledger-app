package com.jml.domain.repository;

import com.jml.domain.dto.InvoiceDTO;

import java.util.List;

/**
 * @author mlukaszuk on 26.04.2022
 */
public interface InvoiceRepository {

	List<InvoiceDTO> findAllInvoices();

	void save(InvoiceDTO invoiceDTO);
}
