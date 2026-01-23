package com.jml.restapi.controller;

import com.jml.domain.service.ContributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mlukaszuk on 18.04.2022
 */
@RestController
@RequestMapping("/contributions")
@RequiredArgsConstructor
public class ContributionController {

	private final ContributionService contributionService;
}
