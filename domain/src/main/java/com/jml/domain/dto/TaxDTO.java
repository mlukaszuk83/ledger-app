package com.jml.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author mlukaszuk on 03.07.2022
 */
@Data
public class TaxDTO {

	private BigDecimal percentage;
	private BigDecimal threshold;

	public boolean hasThreshold() {
		return threshold != null;
	}
}
