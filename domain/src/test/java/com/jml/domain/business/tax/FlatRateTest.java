package com.jml.domain.business.tax;

import com.jml.domain.dto.ContributionDTO;
import com.jml.domain.dto.TaxDTO;
import com.jml.domain.dto.TaxationFormDTO;
import com.jml.domain.enums.ContributionValueType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author mlukaszuk on 24.07.2022
 */
class FlatRateTest {

	@Mock
	private TaxationFormDTO taxationForm;

	@InjectMocks
	private GeneralRules taxCalculator;

	@Test
	void incomeReducedByCosts_whenCostsAreProvided_thenTaxesAreReduced() {

		//given
		BigDecimal income = BigDecimal.TEN;
		BigDecimal costs = BigDecimal.ONE;
		BigDecimal expectedResult = BigDecimal.valueOf(9);

		//when
		var result = taxCalculator.incomeReducedByCosts(income, costs);

		//then
		assertThat(result).isEqualTo(expectedResult);
	}

	/*@Test
	void calculate_whenAllRequiredDataIsProvided_thenTaxToPayAndFinalIncomeAreCalculated() {

		//given
		BigDecimal incomeValue = BigDecimal.valueOf(1000);
		BigDecimal costsValue = BigDecimal.TEN;
		BigDecimal healthValue = BigDecimal.ONE;
		BigDecimal pensionValue = BigDecimal.ONE;
		BigDecimal disabilityValue = BigDecimal.valueOf(2);
		BigDecimal sicknessValue = BigDecimal.valueOf(3);
		BigDecimal accidentValue = BigDecimal.valueOf(4);
		BigDecimal labourFundValue = BigDecimal.valueOf(5);
		BigDecimal tax = BigDecimal.valueOf(0.19);

		BigDecimal expectedTaxToPay = BigDecimal.valueOf(129.6);
		BigDecimal expectedIncome = BigDecimal.valueOf(844.4);

		var firstTax = mockTax(firstTaxThreshold, firstTaxPercentage);
		var secondTax = mockTax(secondTaxThreshold, secondTaxPercentage);

		var health = contributionMock(healthValue, 0.0);
		var pension = contributionMock(pensionValue, 1.0);
		var disability = contributionMock(disabilityValue, 1.0);
		var sickness = contributionMock(sicknessValue, 1.0);
		var accident = contributionMock(accidentValue, 1.0);
		var labourFund = contributionMock(labourFundValue, 0.0);

		IncomeDTO incomeDTO = mock(IncomeDTO.class);
		CostDTO costDTO = mock(CostDTO.class);

		when(taxationForm.getContributions()).thenReturn(List.of(health, pension, disability, sickness, accident, labourFund));
		when(taxationForm.getTaxes()).thenReturn(List.of(firstTax, secondTax));
		when(incomeDTO.getNetValue()).thenReturn(incomeValue);
		when(costDTO.getNetValue()).thenReturn(costsValue);

		//when
		var result = taxCalculator.calculate(incomeDTO, costDTO);

		//then
		assertThat(result.getTaxToPay()).isEqualByComparingTo(expectedTaxToPay);
		assertThat(result.getIncome()).isEqualByComparingTo(expectedIncome);
	}*/

	private TaxDTO mockTax(BigDecimal threshold, BigDecimal percentage) {
		TaxDTO taxDTO = mock(TaxDTO.class);
		when(taxDTO.getThreshold()).thenReturn(threshold);
		if (percentage != null) {
			when(taxDTO.getPercentage()).thenReturn(percentage);
		}
		return taxDTO;
	}

	private ContributionDTO contributionMock(BigDecimal value, Double deductionFactor) {

		ContributionDTO contribution = mock(ContributionDTO.class);

		if (value != null) {
			when(contribution.getValue()).thenReturn(value);
			when(contribution.getDeductionFactor()).thenReturn(BigDecimal.valueOf(deductionFactor));
			doCallRealMethod().when(contribution).getNotDeductibleValue(any(BigDecimal.class));
			doCallRealMethod().when(contribution).getDeductibleValue(any(BigDecimal.class));
			when(contribution.getContributionValueType()).thenReturn(ContributionValueType.DECIMAL);
		}

		return contribution;
	}
}