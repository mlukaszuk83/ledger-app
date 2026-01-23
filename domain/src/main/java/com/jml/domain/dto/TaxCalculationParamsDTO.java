package com.jml.domain.dto;

import lombok.Data;

/**
 * @author mlukaszuk on 03.07.2022
 */
@Data
public class TaxCalculationParamsDTO {

	private IncomeDTO income;
	private CostDTO cost;
}
