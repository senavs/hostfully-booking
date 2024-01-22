package com.senavs.booking.repository;

import com.senavs.booking.model.entity.PropertyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends CrudRepository<PropertyEntity, Long>, PagingAndSortingRepository<PropertyEntity, Long> {
}
