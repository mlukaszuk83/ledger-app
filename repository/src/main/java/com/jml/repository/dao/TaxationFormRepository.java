package com.jml.repository.dao;

import com.jml.repository.model.TaxationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mlukaszuk on 18.04.2022
 */
@Repository
public interface TaxationFormRepository extends JpaRepository<TaxationForm, Long> {
}
