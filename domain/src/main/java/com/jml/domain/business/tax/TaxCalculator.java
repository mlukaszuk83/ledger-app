package com.jml.domain.business.tax;

import com.jml.domain.dto.CostDTO;
import com.jml.domain.dto.IncomeDTO;
import com.jml.domain.dto.TaxCalculationResultDTO;

import java.math.BigDecimal;

/**
 * @author mlukaszuk on 03.07.2022
 */
public interface TaxCalculator {

	default TaxCalculationResultDTO calculate(IncomeDTO income, CostDTO costs) {

		BigDecimal reducedIncome = incomeReducedByContributions(income.getNetValue());
		reducedIncome = incomeReducedByCosts(reducedIncome, costs.getNetValue());
		BigDecimal taxToPay = taxToPay(reducedIncome);
		BigDecimal calculatedIncome = finalIncome(reducedIncome, taxToPay, costs.getNetValue());

		return new TaxCalculationResultDTO(getTaxationFormName(), taxToPay, calculatedIncome);
	}

	String getTaxationFormName();

	BigDecimal healthContribution(BigDecimal income);

	BigDecimal incomeReducedByContributions(BigDecimal income);
	BigDecimal incomeReducedByCosts(BigDecimal income, BigDecimal costs);
	BigDecimal taxToPay(BigDecimal income);
	BigDecimal finalIncome(BigDecimal income, BigDecimal taxToPay, BigDecimal costs);
}
