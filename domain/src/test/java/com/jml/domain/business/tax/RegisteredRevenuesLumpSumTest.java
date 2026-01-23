package com.jml.domain.business.tax;

import com.jml.domain.dto.*;
import com.jml.domain.enums.ContributionValueType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author mlukaszuk on 03.07.2022
 */
@ExtendWith(MockitoExtension.class)
class RegisteredRevenuesLumpSumTest {

	@Mock
	private TaxationFormDTO taxationForm;

	@InjectMocks
	private RegisteredRevenuesLumpSum taxCalculator;

	@Test
	void incomeReducedByCosts_whenCostsAreProvided_thenTaxesAreNotReduced() {

		//given
		BigDecimal income = BigDecimal.TEN;
		BigDecimal costs = BigDecimal.ONE;

		//when
		var result = taxCalculator.incomeReducedByCosts(income, costs);

		//then
		assertThat(result).isEqualTo(income);
	}

	@Test
	void healthContribution_whenIncomeIsLessOrEqual60000_then9PercentOf60PercentOfAverageIncome() {

		//given
		BigDecimal income = BigDecimal.valueOf(60_000);
		BigDecimal expectedHealthContribution = BigDecimal.valueOf(54);

		//when
		var result = taxCalculator.healthContribution(income);

		//then
		assertThat(result).isEqualTo(expectedHealthContribution);
	}

	@Test
	void healthContribution_whenIncomeIsBetween60001And300000_then9PercentOf100PercentOfAverageIncome() {

		//given
		BigDecimal income = BigDecimal.valueOf(300_000);
		BigDecimal expectedHealthContribution = BigDecimal.valueOf(90);

		//when
		var result = taxCalculator.healthContribution(income);

		//then
		assertThat(result).isEqualTo(expectedHealthContribution);
	}

	@Test
	void healthContribution_whenIncomeIsHigherThan300000_then9PercentOf180PercentOfAverageIncome() {

		//given
		BigDecimal income = BigDecimal.valueOf(301_000);
		BigDecimal expectedHealthContribution = BigDecimal.valueOf(162);

		//when
		var result = taxCalculator.healthContribution(income);

		//then
		assertThat(result).isEqualTo(expectedHealthContribution);
	}

	@Test
	void calculate_whenAllRequiredDataIsProvided_thenTaxToPayAndFinalIncomeAreCalculated() {

		//given
		double healthContributionDeductionFactor = 0.5;
		BigDecimal incomeValue = BigDecimal.valueOf(100);
		BigDecimal costsValue = BigDecimal.TEN;
		BigDecimal healthValue = BigDecimal.ONE;
		BigDecimal pensionValue = BigDecimal.ONE;
		BigDecimal disabilityValue = BigDecimal.valueOf(2);
		BigDecimal sicknessValue = BigDecimal.valueOf(3);
		BigDecimal accidentValue = BigDecimal.valueOf(4);
		BigDecimal labourFundValue = BigDecimal.valueOf(5);
		BigDecimal taxPercentage = BigDecimal.valueOf(0.12);

		BigDecimal expectedTaxToPay = BigDecimal.valueOf(10.62);
		BigDecimal expectedIncome = BigDecimal.valueOf(62.38);

		var tax = mockTax(taxPercentage);

		var health = contributionMock(healthValue, healthContributionDeductionFactor, ContributionValueType.PERCENTAGE);
		var pension = contributionMock(pensionValue, 1.0, ContributionValueType.DECIMAL);
		var disability = contributionMock(disabilityValue, 1.0, ContributionValueType.DECIMAL);
		var sickness = contributionMock(sicknessValue, 1.0, ContributionValueType.DECIMAL);
		var accident = contributionMock(accidentValue, 1.0, ContributionValueType.DECIMAL);
		var labourFund = contributionMock(labourFundValue, 0.0, ContributionValueType.DECIMAL);

		IncomeDTO incomeDTO = mock(IncomeDTO.class);
		CostDTO costDTO = mock(CostDTO.class);

		when(taxationForm.getContributions()).thenReturn(List.of(health, pension, disability, sickness, accident, labourFund));
		when(taxationForm.getFirstTaxOnlyWhenNoThresholds()).thenReturn(tax);
		when(incomeDTO.getNetValue()).thenReturn(incomeValue);
		when(costDTO.getNetValue()).thenReturn(costsValue);

		//when
		var result = taxCalculator.calculate(incomeDTO, costDTO);

		//then
		verify(tax).getPercentage();
		verify(tax, never()).getThreshold();

		assertThat(result.getTaxToPay()).isEqualByComparingTo(expectedTaxToPay);
		assertThat(result.getIncome()).isEqualByComparingTo(expectedIncome);
	}

	private TaxDTO mockTax(BigDecimal percentage) {

		TaxDTO twelvePercent = mock(TaxDTO.class);
		when(twelvePercent.getPercentage()).thenReturn(percentage);

		return twelvePercent;
	}

	private ContributionDTO contributionMock(BigDecimal value, Double deductionFactor, ContributionValueType type) {

		ContributionDTO contribution = mock(ContributionDTO.class);

		if (value != null) {
			when(contribution.getValue()).thenReturn(value);
			when(contribution.getContributionValueType()).thenReturn(type);
			when(contribution.getDeductionFactor()).thenReturn(BigDecimal.valueOf(deductionFactor));
			doCallRealMethod().when(contribution).getNotDeductibleValue(any(BigDecimal.class));
			doCallRealMethod().when(contribution).getDeductibleValue(any(BigDecimal.class));
		}

		return contribution;
	}
}