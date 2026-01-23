package com.jml.restapi.controller;

import com.jml.domain.dto.TaxCalculationParamsDTO;
import com.jml.domain.dto.TaxCalculationResultDTO;
import com.jml.domain.service.TaxCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mlukaszuk on 03.07.2022
 */
@RestController
@RequestMapping("/taxCalculator")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class TaxCalculatorController {

	private final TaxCalculatorService taxCalculatorService;

	@PostMapping("/calculate")
	List<TaxCalculationResultDTO> calculate(@RequestBody TaxCalculationParamsDTO paramsDTO) {
		return taxCalculatorService.calculate(paramsDTO);
	}
}
