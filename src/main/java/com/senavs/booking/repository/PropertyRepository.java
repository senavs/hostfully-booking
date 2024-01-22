package com.senavs.booking.repository;

import com.senavs.booking.model.entity.PropertyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends CrudRepository<PropertyEntity, Long>, PagingAndSortingRepository<PropertyEntity, Long> {

    List<PropertyEntity> findByOwnerTaxId(final String ownerTaxId);
}
