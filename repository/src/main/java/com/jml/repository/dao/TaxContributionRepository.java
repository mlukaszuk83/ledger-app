package com.jml.repository.dao;

import com.jml.repository.model.TaxContribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mlukaszuk on 18.04.2022
 */
@Repository
public interface TaxContributionRepository extends JpaRepository<TaxContribution, Long> {
}
