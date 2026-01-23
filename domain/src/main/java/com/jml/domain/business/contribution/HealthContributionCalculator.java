package com.jml.domain.business.contribution;

import java.math.BigDecimal;

/**
 * @author mlukaszuk on 24.07.2022
 */
public interface HealthContributionCalculator {

	BigDecimal calculate(BigDecimal income);
}
