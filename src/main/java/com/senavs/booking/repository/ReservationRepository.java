package com.senavs.booking.repository;

import com.senavs.booking.model.entity.ReservationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<ReservationEntity, Long>,
        PagingAndSortingRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByPropertyId(final Long propertyId);
}
