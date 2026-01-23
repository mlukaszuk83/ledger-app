package com.jml.domain.dto;

import com.jml.domain.enums.ContributionType;
import com.jml.domain.enums.ContributionValueType;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author mlukaszuk on 18.04.2022
 */
@Data
public class ContributionDTO {

	private String name;

	private BigDecimal value;

	@Min(value = 0)
	@Max(value = 1)
	private BigDecimal deductionFactor;
	private BigDecimal threshold;

	private LocalDate validFrom;
	private LocalDate validTo;

	private ContributionType contributionType;
	private ContributionValueType contributionValueType;

	public BigDecimal getDeductibleValue(BigDecimal income) {
		return contributionValue(income)
				.multiply(getDeductionFactor());
	}

	public BigDecimal getNotDeductibleValue(BigDecimal income) {
		return contributionValue(income)
				.multiply(BigDecimal.ONE.subtract(getDeductionFactor()));
	}

	private BigDecimal contributionValue(BigDecimal income) {
		BigDecimal val;
		ContributionValueType type = getContributionValueType();
		switch (type) {
			case DECIMAL -> val = getValue();
			case PERCENTAGE -> val = income.multiply(getValue());
			default -> val = BigDecimal.ZERO;
		}
		return val;
	}
}
