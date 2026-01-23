package com.jml.domain.business.tax;

import com.jml.domain.dto.TaxationFormDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

/**
 * @author mlukaszuk on 11.07.2022
 */
@RequiredArgsConstructor
@Getter
abstract class AbstractTaxCalculator implements TaxCalculator {

	private final TaxationFormDTO taxationForm;

	@Override
	public String getTaxationFormName() {
		return taxationForm.getName();
	}

	@Override
	public BigDecimal incomeReducedByContributions(BigDecimal income) {
		return taxationForm.getContributions().stream()
				.map(contribution -> contribution.getDeductibleValue(income))
				.reduce(income, BigDecimal::subtract);
	}

	@Override
	public BigDecimal finalIncome(BigDecimal income, BigDecimal taxToPay, BigDecimal costs) {
		return taxationForm.getContributions().stream()
				.map(contribution -> contribution.getNotDeductibleValue(income))
				.reduce(income, BigDecimal::subtract)
				.subtract(taxToPay);
	}
}
