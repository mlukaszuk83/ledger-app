package com.jml.repository.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author mlukaszuk on 18.04.2022
 */
@Entity
@Table(name = "taxContribution")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TaxContribution {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "taxationFormId", referencedColumnName = "id")
	private TaxationForm taxationForm;

	@ManyToOne
	@JoinColumn(name = "contributionId", referencedColumnName = "id")
	private Contribution contribution;

	private String deductionType;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TaxContribution that)) {
			return false;
		}
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
