package com.jml.repository.dao;

import com.jml.domain.dto.InvoiceDTO;
import com.jml.repository.mapper.InvoiceMapper;
import com.jml.repository.model.Invoice;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author mlukaszuk on 18.04.2022
 */
@Repository
public class InvoiceRepository extends SimpleJpaRepository<Invoice, Long> implements com.jml.domain.repository.InvoiceRepository {

	private final InvoiceMapper invoiceMapper;

	public InvoiceRepository(EntityManager em, InvoiceMapper invoiceMapper) {
		super(Invoice.class, em);
		this.invoiceMapper = invoiceMapper;
	}

	@Override
	public void save(InvoiceDTO invoiceDTO) {
		Invoice invoice = invoiceMapper.fromDTO(invoiceDTO);
		save(invoice);
	}

	@Override
	public List<InvoiceDTO> findAllInvoices() {
		return findAll().stream()
				.map(invoiceMapper::toDTO)
				.toList();
	}
}
