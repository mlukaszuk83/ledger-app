package com.jml.domain.service;

import com.jml.domain.dto.InvoiceDTO;
import com.jml.domain.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mlukaszuk on 18.04.2022
 */
@Service
@RequiredArgsConstructor
public class InvoiceService {

	private final InvoiceRepository invoiceRepository;

	public List<InvoiceDTO> getAll() {
		return invoiceRepository.findAllInvoices();
	}

	public void add(InvoiceDTO invoiceDTO) {
		invoiceRepository.save(invoiceDTO);
	}
}
