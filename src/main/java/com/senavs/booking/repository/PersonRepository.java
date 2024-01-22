package com.senavs.booking.repository;

import com.senavs.booking.model.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long>, PagingAndSortingRepository<PersonEntity, Long> {

    PersonEntity findByTaxId(final String taxId);
}
