package com.senavs.booking.repository;

import com.senavs.booking.model.entity.ReservationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<ReservationEntity, Long>,
        PagingAndSortingRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByPropertyId(final Long propertyId);

    // TODO: refactor this
    Optional<ReservationEntity> findByPropertyIdAndCheckInBetweenOrCheckOutBetween(final Long propertyId,
                                                                                   final LocalDate checkIn,
                                                                                   final LocalDate checkOut,
                                                                                   final LocalDate checkIn2,
                                                                                   final LocalDate checkOut2);
}
