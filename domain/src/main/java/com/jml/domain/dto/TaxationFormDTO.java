package com.jml.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * @author mlukaszuk on 18.04.2022
 */
@Data
public class TaxationFormDTO {

	private String name;

	private Integer accountingYear;

	private List<TaxDTO> taxes;
	private List<ContributionDTO> contributions;

	public TaxDTO getFirstTaxOnlyWhenNoThresholds() {

		if (taxes.stream().anyMatch(TaxDTO::hasThreshold)) {
			throw new UnsupportedOperationException("Threshold available, calculations should include them!");
		}

		return taxes.iterator().next();
	}
}
