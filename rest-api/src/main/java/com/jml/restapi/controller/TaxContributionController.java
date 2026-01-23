package com.jml.restapi.controller;

import com.jml.domain.service.TaxContributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mlukaszuk on 18.04.2022
 */
@RestController
@RequestMapping("/tax/contributions")
@RequiredArgsConstructor
public class TaxContributionController {

	private final TaxContributionService taxContributionService;
}
