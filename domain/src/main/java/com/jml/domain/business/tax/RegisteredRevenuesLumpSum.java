package com.jml.domain.business.tax;

import com.jml.domain.dto.TaxDTO;
import com.jml.domain.dto.TaxationFormDTO;

import java.math.BigDecimal;

/**
 * @author mlukaszuk on 03.07.2022
 */
class RegisteredRevenuesLumpSum extends AbstractTaxCalculator {

	public RegisteredRevenuesLumpSum(TaxationFormDTO taxationForm) {
		super(taxationForm);
	}

	@Override
	public BigDecimal incomeReducedByCosts(BigDecimal income, BigDecimal unused) {
		return income;
	}

	@Override
	public BigDecimal healthContribution(BigDecimal income) {
		if (income.compareTo(BigDecimal.valueOf(60_000)) <= 0) {
			return
		}
	}

	@Override
	public BigDecimal taxToPay(BigDecimal income) {
		TaxDTO applicableTax = getTaxationForm().getFirstTaxOnlyWhenNoThresholds();
		return income.multiply(applicableTax.getPercentage());
	}

	@Override
	public BigDecimal finalIncome(BigDecimal income, BigDecimal taxToPay, BigDecimal costs) {
		return super.finalIncome(income, taxToPay, costs)
				.subtract(costs);
	}
}
