package com.jml.domain.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author mlukaszuk on 03.07.2022
 */
class TaxationFormDTOTest {

	private final TaxationFormDTO taxationFormDTO = new TaxationFormDTO();

	@Test
	void whenTaxesDoNotHaveThresholdOnlyFirstIsIncludedInCalculations() {

		//given
		TaxDTO first = new TaxDTO();
		first.setPercentage(BigDecimal.ONE);
		TaxDTO second = new TaxDTO();
		second.setPercentage(BigDecimal.TEN);

		taxationFormDTO.setTaxes(List.of(first, second));

		//when
		var result = taxationFormDTO.getFirstTaxOnlyWhenNoThresholds();

		//then
		assertThat(result).isEqualTo(first);
	}

	@Test
	void whenAtLeastOneTaxDoesHaveThresholdThanExceptionIsThrown() {

		//given
		TaxDTO first = new TaxDTO();
		first.setPercentage(BigDecimal.ONE);
		TaxDTO second = new TaxDTO();
		second.setPercentage(BigDecimal.TEN);
		second.setThreshold(BigDecimal.ONE);

		taxationFormDTO.setTaxes(List.of(first, second));

		//when-then
		assertThatThrownBy(taxationFormDTO::getFirstTaxOnlyWhenNoThresholds)
				.isInstanceOf(UnsupportedOperationException.class)
				.hasMessage("Threshold available, calculations should include them!");
	}
}