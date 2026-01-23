package com.jml.repository.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author mlukaszuk on 18.04.2022
 */
@Entity
@Table(name = "invoice")
@Getter
@Setter
@ToString
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String invoiceType;
	private String invoiceKind;

	private BigDecimal netValue;
	private BigDecimal grossValue;

	private LocalDate issueDate;
	private LocalDate saleDate;

	@CreationTimestamp
	private LocalDateTime createdOn;

	@UpdateTimestamp
	private LocalDateTime updatedOn;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Invoice invoice)) {
			return false;
		}
		return Objects.equals(id, invoice.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
