package com.jml.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author mlukaszuk on 03.07.2022
 */
@Data
public class TaxCalculationResultDTO {

	private final String name;
	private final BigDecimal taxToPay;
	private final BigDecimal income;
}
