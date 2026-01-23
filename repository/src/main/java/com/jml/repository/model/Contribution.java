package com.jml.repository.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author mlukaszuk on 18.04.2022
 */
@Entity
@Table(name = "contribution")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Contribution {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String contributionType;

	private BigDecimal value;

	private LocalDate validFrom;
	private LocalDate validTo;

	@OneToMany(mappedBy = "contribution")
	@ToString.Exclude
	private List<TaxContribution> taxContributions;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Contribution that)) {
			return false;
		}
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
