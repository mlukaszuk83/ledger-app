package com.jml.domain.dto;

import com.jml.domain.enums.DeductionType;
import lombok.Data;

/**
 * @author mlukaszuk on 18.04.2022
 */
@Data
public class TaxContributionDTO {

	private TaxationFormDTO taxationForm;

	private com.jml.domain.dto.ContributionDTO contribution;

	private DeductionType deductionType;
}
