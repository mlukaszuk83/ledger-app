package com.jml.domain.dto;

import com.jml.domain.enums.InvoiceKind;
import com.jml.domain.enums.InvoiceType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author mlukaszuk on 18.04.2022
 */
@Data
public class InvoiceDTO {

	private String name;

	private BigDecimal netValue;
	private BigDecimal grossValue;

	private LocalDate issueDate;
	private LocalDate saleDate;

	private InvoiceType invoiceType;
	private InvoiceKind invoiceKind;
}
