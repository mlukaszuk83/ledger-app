package com.jml.domain.business.tax;

import com.jml.domain.dto.TaxDTO;
import com.jml.domain.dto.TaxationFormDTO;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

/**
 * @author mlukaszuk on 03.07.2022
 */
class GeneralRules extends AbstractTaxCalculator {

	public GeneralRules(TaxationFormDTO taxationForm) {
		super(taxationForm);
	}

	@Override
	public BigDecimal incomeReducedByCosts(BigDecimal income, BigDecimal costs) {
		return income.subtract(costs);
	}

	@Override
	public BigDecimal taxToPay(BigDecimal income) {
		AtomicReference<TaxCalculation> previousCalculation = new AtomicReference<>(TaxCalculation.empty());
		return getTaxationForm()
				.getTaxes()
				.stream()
				.sorted(Comparator.comparing(TaxDTO::getThreshold).reversed())
				.map(toThresholdTax(income, previousCalculation))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private Function<TaxDTO, BigDecimal> toThresholdTax(BigDecimal income, AtomicReference<TaxCalculation> previousCalculation) {
		return tax -> {
			previousCalculation.set(calculateTaxToPay(income, previousCalculation.get(), tax));
			return previousCalculation.get().tax;
		};
	}

	private TaxCalculation calculateTaxToPay(BigDecimal income, TaxCalculation previousCalculation, TaxDTO tax) {

		if (income.compareTo(tax.getThreshold()) > 0) {
			BigDecimal thresholdIncome = income.subtract(previousCalculation.thresholdIncome).subtract(tax.getThreshold());
			BigDecimal calculatedTax = thresholdIncome.multiply(tax.getPercentage());
			return new TaxCalculation(calculatedTax, thresholdIncome);
		}

		return previousCalculation;
	}

	private record TaxCalculation(BigDecimal tax, BigDecimal thresholdIncome) {

		static TaxCalculation empty() {
			return new TaxCalculation(BigDecimal.ZERO, BigDecimal.ZERO);
		}
	}
}
