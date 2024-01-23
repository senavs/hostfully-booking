package com.senavs.booking.repository;

import com.senavs.booking.model.entity.ReservationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<ReservationEntity, Long>,
        PagingAndSortingRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByPropertyIdAndIsDeletedFalse(final Long propertyId);

    @Query("SELECT re FROM ReservationEntity re " +
            "WHERE re.property.id = ?1 AND " +
            "re.isDeleted = FALSE AND " +
            // new reservation date inside any saved reservation date range
            "(((re.checkIn BETWEEN ?2 AND ?3) OR (re.checkOut BETWEEN ?2 AND ?3)) OR " +
            // any saved reservation date range inside new reservation date
            "((?2 BETWEEN re.checkIn AND re.checkOut) OR (?3 BETWEEN re.checkIn AND re.checkOut)))")
    List<ReservationEntity> findReservationAvailableSameDateRange(final Long propertyId, final LocalDate checkIn, final LocalDate checkOut);
}
