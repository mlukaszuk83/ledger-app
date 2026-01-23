package com.jml.domain.service;

import com.jml.domain.dto.TaxCalculationParamsDTO;
import com.jml.domain.dto.TaxCalculationResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author mlukaszuk on 03.07.2022
 */
@Service
@RequiredArgsConstructor
public class TaxCalculatorService {
	public List<TaxCalculationResultDTO> calculate(TaxCalculationParamsDTO paramsDTO) {

		BigDecimal incomeMinusCost = paramsDTO.getIncome().getNetValue().subtract(paramsDTO.getCost().getNetValue());
		BigDecimal taxToPay = BigDecimal.valueOf(0.12).multiply(incomeMinusCost);
		TaxCalculationResultDTO skala = new TaxCalculationResultDTO("Skala podatkowa", taxToPay, incomeMinusCost.subtract(taxToPay));

		taxToPay = BigDecimal.valueOf(0.12).multiply(paramsDTO.getIncome().getNetValue());
		TaxCalculationResultDTO ryczalt = new TaxCalculationResultDTO("Ryczałt od przychodów", taxToPay, paramsDTO.getIncome().getNetValue().subtract(taxToPay));

		return List.of(skala, ryczalt);
	}
}
