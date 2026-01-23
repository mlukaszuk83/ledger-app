package com.jml.domain.business.contribution;

import com.jml.domain.dto.ContributionDTO;
import com.jml.domain.enums.ContributionType;
import com.jml.domain.enums.ContributionValueType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author mlukaszuk on 24.07.2022
 */
class RegisteredRevenuesLumpSumHealthContributionTest {

	private final List<ContributionDTO> contributions = mock(List.class);
	private final BigDecimal averageIncome = BigDecimal.valueOf(2000);
	private final RegisteredRevenuesLumpSumHealthContribution calculator = new RegisteredRevenuesLumpSumHealthContribution(contributions, averageIncome);

	@Test
	void calculate_whenIncomeIsLessOrEqualFirstThreshold_then9PercentOf60PercentOfAverageIncome() {

		//given
		BigDecimal income = BigDecimal.valueOf(1000);
		BigDecimal expected = BigDecimal.valueOf(108);
		when(contributions.stream()).thenReturn(Stream.of(mockContribution(1000), mockContribution(2000)));

		//when
		var result = calculator.calculate(income);

		//then
		assertThat(result).isEqualByComparingTo(expected);
	}

	private ContributionDTO mockContribution(double threshold) {

		ContributionDTO dto = mock(ContributionDTO.class);
		when(dto.getContributionType()).thenReturn(ContributionType.HEALTH);
		when(dto.getContributionValueType()).thenReturn(ContributionValueType.PERCENTAGE);
		when(dto.getValue()).thenReturn(BigDecimal.valueOf(0.09));
		when(dto.getThreshold()).thenReturn(BigDecimal.valueOf(threshold));
		return dto;
	}
}