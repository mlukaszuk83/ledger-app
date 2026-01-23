package com.jml.domain.business.tax;

import com.jml.domain.dto.TaxDTO;
import com.jml.domain.dto.TaxationFormDTO;

import java.math.BigDecimal;

/**
 * @author mlukaszuk on 03.07.2022
 */
class FlatRate extends AbstractTaxCalculator {

	public FlatRate(TaxationFormDTO taxationForm) {
		super(taxationForm);
	}

	@Override
	public BigDecimal incomeReducedByCosts(BigDecimal income, BigDecimal costs) {
		return income.subtract(costs);
	}

	@Override
	public BigDecimal taxToPay(BigDecimal income) {
		TaxDTO applicableTax = getTaxationForm().getFirstTaxOnlyWhenNoThresholds();
		return income.multiply(applicableTax.getPercentage());
	}
}
