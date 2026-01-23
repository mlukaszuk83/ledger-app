package com.jml.domain.business.tax;

import com.jml.domain.dto.ContributionDTO;
import com.jml.domain.dto.TaxationFormDTO;
import com.jml.domain.enums.ContributionValueType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

/**
 * @author mlukaszuk on 11.07.2022
 */
class AbstractTaxCalculatorTest {

	private final TaxationFormDTO taxationForm = mock(TaxationFormDTO.class);

	private final AbstractTaxCalculator taxCalculator = new AbstractTaxCalculator(taxationForm) {
		@Override
		public BigDecimal incomeReducedByCosts(BigDecimal income, BigDecimal costs) {
			return null;
		}

		@Override
		public BigDecimal taxToPay(BigDecimal income) {
			return null;
		}
	};

	@Test
	void getTaxationFormName_shouldReturnNameFromTaxationForm() {

		//given
		String name = "Registered Revenues Lump Sum";
		when(taxationForm.getName()).thenReturn(name);

		//when
		var result = taxCalculator.getTaxationFormName();

		//then
		assertThat(result).isEqualTo(name);
	}

	@Test
	void incomeReducedByContributions_whenContributionsDeductionFactorIsZero_thenTaxesAreNotReduced() {

		//given
		BigDecimal income = BigDecimal.TEN;

		var labourFund = mock(ContributionDTO.class);
		when(labourFund.getValue()).thenReturn(BigDecimal.ONE);
		when(labourFund.getContributionValueType()).thenReturn(ContributionValueType.DECIMAL);
		when(labourFund.getDeductionFactor()).thenReturn(BigDecimal.ZERO);
		when(labourFund.getDeductibleValue(eq(income))).thenCallRealMethod();

		var contributions = List.of(labourFund);

		when(taxationForm.getContributions()).thenReturn(contributions);

		//when
		BigDecimal result = taxCalculator.incomeReducedByContributions(income);

		//then
		verify(labourFund).getValue();
		assertThat(result).isEqualByComparingTo(income);
	}

	@Test
	void incomeReducedByContributions_whenContributionsDeductionFactorIsBiggerThanZero_thenTaxIsReducedByTheGivenFactor() {

		//given
		BigDecimal healthContributionDeductionFactor = BigDecimal.valueOf(0.7);
		BigDecimal healthValue = BigDecimal.ONE;
		BigDecimal income = BigDecimal.TEN;
		BigDecimal expectedResult = income.subtract(healthValue.multiply(healthContributionDeductionFactor));

		var health = mock(ContributionDTO.class);
		when(health.getValue()).thenReturn(healthValue);
		when(health.getContributionValueType()).thenReturn(ContributionValueType.DECIMAL);
		when(health.getDeductionFactor()).thenReturn(healthContributionDeductionFactor);
		when(health.getDeductibleValue(eq(income))).thenCallRealMethod();

		var contributions = List.of(health);

		when(taxationForm.getContributions()).thenReturn(contributions);

		//when
		var result = taxCalculator.incomeReducedByContributions(income);

		//then
		verify(health).getValue();
		assertThat(result).isEqualByComparingTo(expectedResult);
	}

	@Test
	void finalIncome_whenContributionsDeductionFactorIsBetweenZeroAndOne_thenFinalIncomeIsReducedByTheReverseOfGivenFactor() {

		//given
		BigDecimal healthContributionDeductionFactor = BigDecimal.valueOf(0.7);
		BigDecimal healthValue = BigDecimal.valueOf(3);
		BigDecimal labourFundValue = BigDecimal.valueOf(5);
		BigDecimal income = BigDecimal.valueOf(100);
		BigDecimal taxToPay = BigDecimal.valueOf(2);
		BigDecimal costs = BigDecimal.ONE;

		BigDecimal expectedIncome = income.subtract(taxToPay)
				.subtract(costs)
				.subtract(healthValue)
				.subtract(labourFundValue)
				.add(healthValue.multiply(healthContributionDeductionFactor));

		var health = mock(ContributionDTO.class);
		when(health.getValue()).thenReturn(healthValue);
		when(health.getContributionValueType()).thenReturn(ContributionValueType.DECIMAL);
		when(health.getDeductionFactor()).thenReturn(healthContributionDeductionFactor);
		when(health.getNotDeductibleValue(eq(income))).thenCallRealMethod();

		var labourFund = mock(ContributionDTO.class);
		when(labourFund.getValue()).thenReturn(labourFundValue);
		when(labourFund.getDeductionFactor()).thenReturn(BigDecimal.ZERO);
		when(labourFund.getNotDeductibleValue(eq(income))).thenCallRealMethod();

		var contributions = List.of(health, labourFund);

		when(taxationForm.getContributions()).thenReturn(contributions);

		//when
		var result = taxCalculator.finalIncome(income, taxToPay, costs);

		//then
		verify(health).getValue();
		verify(labourFund).getValue();
		assertThat(result).isEqualByComparingTo(expectedIncome);
	}
}