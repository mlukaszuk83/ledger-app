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
 * @author mlukaszuk on 10.07.2022
 */
@ExtendWith(MockitoExtension.class)
class GeneralRulesTest {

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

	@Test
	void taxToPay_whenIncomeIsLesserEqualFirstThreshold_thenNoTaxToPay() {

		//given
		BigDecimal income = BigDecimal.valueOf(30_000);
		BigDecimal expectedResult = BigDecimal.ZERO;
		TaxDTO firstTax = mockTax(BigDecimal.valueOf(30_000));
		TaxDTO secondTax = mockTax(BigDecimal.valueOf(120_000));
		when(taxationForm.getTaxes()).thenReturn(List.of(firstTax, secondTax));

		//when
		var result = taxCalculator.taxToPay(income);

		//then
		assertThat(result)
				.isEqualByComparingTo(expectedResult);
	}

	@Test
	void taxToPay_whenIncomeIsLesserEqualSecondThreshold_thenTaxToPayIsCalculatedFromIncomeReducedByFirstThreshold() {

		//given
		BigDecimal income = BigDecimal.valueOf(90_000);
		BigDecimal expectedResult = BigDecimal.valueOf(7_200);
		TaxDTO firstTax = mockTax(BigDecimal.valueOf(30_000), BigDecimal.valueOf(0.12));
		TaxDTO secondTax = mockTax(BigDecimal.valueOf(120_000));
		when(taxationForm.getTaxes()).thenReturn(List.of(firstTax, secondTax));

		//when
		var result = taxCalculator.taxToPay(income);

		//then
		assertThat(result)
				.isEqualByComparingTo(expectedResult);
	}

	@Test
	void taxToPay_whenIncomeIsGraterThanSecondThreshold_thenTaxToPayIsTaxFromSecondThresholdPlusPercentageOfIncomeAboveSecondThreshold() {

		//given
		BigDecimal income = BigDecimal.valueOf(150_000);
		BigDecimal expectedResult = BigDecimal.valueOf(20_400);
		TaxDTO firstTax = mockTax(BigDecimal.valueOf(30_000), BigDecimal.valueOf(0.12));
		TaxDTO secondTax = mockTax(BigDecimal.valueOf(120_000), BigDecimal.valueOf(0.32));
		when(taxationForm.getTaxes()).thenReturn(List.of(firstTax, secondTax));

		//when
		var result = taxCalculator.taxToPay(income);

		//then
		assertThat(result)
				.isEqualByComparingTo(expectedResult);
	}

	@Test
	void calculate_whenAllRequiredDataIsProvided_thenTaxToPayAndFinalIncomeAreCalculated() {

		//given
		BigDecimal incomeValue = BigDecimal.valueOf(1000);
		BigDecimal costsValue = BigDecimal.TEN;
		BigDecimal pensionValue = BigDecimal.ONE;
		BigDecimal disabilityValue = BigDecimal.valueOf(2);
		BigDecimal sicknessValue = BigDecimal.valueOf(3);
		BigDecimal accidentValue = BigDecimal.valueOf(4);
		BigDecimal labourFundValue = BigDecimal.valueOf(5);
		BigDecimal firstTaxPercentage = BigDecimal.valueOf(0.12);
		BigDecimal secondTaxPercentage = BigDecimal.valueOf(0.32);
		BigDecimal firstTaxThreshold = BigDecimal.valueOf(200);
		BigDecimal secondTaxThreshold = BigDecimal.valueOf(800);

		BigDecimal healthValue = BigDecimal.valueOf(0.09);

		BigDecimal expectedTaxToPay = BigDecimal.valueOf(129.6);
		BigDecimal expectedIncome = BigDecimal.valueOf(761.3);

		var firstTax = mockTax(firstTaxThreshold, firstTaxPercentage);
		var secondTax = mockTax(secondTaxThreshold, secondTaxPercentage);

		var health = contributionMock(healthValue, 0.0, ContributionValueType.PERCENTAGE);
		var pension = contributionMock(pensionValue, 1.0, ContributionValueType.DECIMAL);
		var disability = contributionMock(disabilityValue, 1.0, ContributionValueType.DECIMAL);
		var sickness = contributionMock(sicknessValue, 1.0, ContributionValueType.DECIMAL);
		var accident = contributionMock(accidentValue, 1.0, ContributionValueType.DECIMAL);
		var labourFund = contributionMock(labourFundValue, 0.0, ContributionValueType.DECIMAL);

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
	}

	private TaxDTO mockTax(BigDecimal threshold) {
		return mockTax(threshold, null);
	}

	private TaxDTO mockTax(BigDecimal threshold, BigDecimal percentage) {
		TaxDTO taxDTO = mock(TaxDTO.class);
		when(taxDTO.getThreshold()).thenReturn(threshold);
		if (percentage != null) {
			when(taxDTO.getPercentage()).thenReturn(percentage);
		}
		return taxDTO;
	}

	private ContributionDTO contributionMock(BigDecimal value, Double deductionFactor, ContributionValueType type) {

		ContributionDTO contribution = mock(ContributionDTO.class);

		if (value != null) {
			when(contribution.getValue()).thenReturn(value);
			when(contribution.getDeductionFactor()).thenReturn(BigDecimal.valueOf(deductionFactor));
			doCallRealMethod().when(contribution).getNotDeductibleValue(any(BigDecimal.class));
			doCallRealMethod().when(contribution).getDeductibleValue(any(BigDecimal.class));
			when(contribution.getContributionValueType()).thenReturn(type);
		}

		return contribution;
	}
}