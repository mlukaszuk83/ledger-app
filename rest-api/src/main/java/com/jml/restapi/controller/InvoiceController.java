package com.jml.restapi.controller;

import com.jml.domain.dto.InvoiceDTO;
import com.jml.domain.enums.InvoiceKind;
import com.jml.domain.enums.InvoiceType;
import com.jml.domain.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mlukaszuk on 18.04.2022
 */
@RestController
@RequestMapping("/invoices")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class InvoiceController {

	private final InvoiceService invoiceService;

	@GetMapping
	List<InvoiceDTO> getInvoices() {
		return invoiceService.getAll();
	}

	@GetMapping("/types")
	InvoiceType[] getInvoiceTypes() {
		return InvoiceType.values();
	}

	@GetMapping("/kinds")
	InvoiceKind[] getInvoiceKinds() {
		return InvoiceKind.values();
	}

	@PostMapping("/add")
	void addInvoice(@RequestBody InvoiceDTO invoiceDTO) {
		invoiceService.add(invoiceDTO);
	}
}
