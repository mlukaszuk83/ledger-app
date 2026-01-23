package com.jml.repository.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author mlukaszuk on 18.04.2022
 */
@Entity
@Table(name = "taxationForm")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TaxationForm {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private BigDecimal taxPercentage;

	private Integer accountingYear;

	@OneToMany(mappedBy = "taxationForm", fetch = FetchType.EAGER)
	private List<TaxContribution> taxContributions;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TaxationForm that)) {
			return false;
		}
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
