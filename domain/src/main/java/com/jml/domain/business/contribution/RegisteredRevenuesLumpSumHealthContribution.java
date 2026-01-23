package com.jml.domain.business.contribution;

import com.jml.domain.dto.ContributionDTO;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author mlukaszuk on 24.07.2022
 */
@RequiredArgsConstructor
public class RegisteredRevenuesLumpSumHealthContribution implements HealthContributionCalculator {

	private final List<ContributionDTO> contributions;
	private final BigDecimal averageIncome;

	@Override
	public BigDecimal calculate(BigDecimal income) {
		return null;
	}
}
