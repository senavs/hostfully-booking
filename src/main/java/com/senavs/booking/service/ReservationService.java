package com.senavs.booking.service;

import com.senavs.booking.model.entity.PersonEntity;
import com.senavs.booking.model.entity.PropertyEntity;
import com.senavs.booking.model.entity.ReservationEntity;
import com.senavs.booking.model.request.RegisterReservationRequest;
import com.senavs.booking.repository.PersonRepository;
import com.senavs.booking.repository.PropertyRepository;
import com.senavs.booking.repository.ReservationRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final PropertyRepository propertyRepository;
    private final PersonRepository personRepository;
    private final ReservationRepository reservationRepository;

    public final List<ReservationEntity> listAllOwnerProperties(final Long propertyId) {
        return reservationRepository.findByPropertyIdAndIsDeletedFalse(propertyId);
    }

    public ReservationEntity findReservation(final Long reservationId) {
        final Optional<ReservationEntity> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isEmpty()) {
            throw new InvalidParameterException("there is no reservation with the provided id");
        }
        return reservationOptional.get();
    }

    public ReservationEntity bookProperty(final RegisterReservationRequest request) {
        verifyReservationDateAvailability(request.getPropertyId(), request.getCheckIn(), request.getCheckOut());

        final Optional<PropertyEntity> propertyOptional = propertyRepository.findById(request.getPropertyId());
        if (propertyOptional.isEmpty()) {
            throw new InvalidParameterException("property was not found");
        }

        final List<PersonEntity> guests = personRepository.findByTaxIdIn(request.getGuests());
        if (guests.size() != request.getGuests().size()) {
            throw new InvalidParameterException("not al guests are registered");
        }

        final ReservationEntity reservation = ReservationEntity.builder()
                .isBlockedByOwner(request.getBlockedByOwner())
                .property(propertyOptional.get())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .guests(guests)
                .isDeleted(false)
                .build();

        return reservationRepository.save(reservation);
    }

    public ReservationEntity rebookProperty(final Long reservationId) {
        final ReservationEntity reservation = this.findReservation(reservationId);

        verifyReservationDateAvailability(reservation.getProperty().getId(), reservation.getCheckIn(), reservation.getCheckOut());

        reservation.setIsDeleted(false);
        reservationRepository.save(reservation);
        return reservation;
    }

    public void deleteReservation(final Long reservationId) {
        final ReservationEntity reservation = this.findReservation(reservationId);
        reservation.setIsDeleted(true);
        reservationRepository.save(reservation);
    }

    private void verifyReservationDateAvailability(final Long propertyId,
                                                   final LocalDate checkIn,
                                                   final LocalDate checkOut) {
        if (checkIn.isAfter(checkOut)) {
            throw new InvalidParameterException("check in cannot be greater than check out");
        }

        List<ReservationEntity> reservations = reservationRepository
                .findReservationAvailableSameDateRange(propertyId, checkIn, checkOut);

        if (!reservations.isEmpty()) {
            final ReservationEntity currentReservation = reservations.get(0);

            if (currentReservation.getIsBlockedByOwner()) {
                throw new InvalidParameterException("property owner blocked new reservations in this date range");
            } else {
                throw new InvalidParameterException("this property is already booked in this period");
            }

        }
    }

}
