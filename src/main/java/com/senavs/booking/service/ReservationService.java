package com.senavs.booking.service;

import com.senavs.booking.model.entity.PersonEntity;
import com.senavs.booking.model.entity.PropertyEntity;
import com.senavs.booking.model.entity.ReservationEntity;
import com.senavs.booking.model.request.RegisterReservationRequest;
import com.senavs.booking.repository.PersonRepository;
import com.senavs.booking.repository.PropertyRepository;
import com.senavs.booking.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final PropertyRepository propertyRepository;
    private final PersonRepository personRepository;
    private final ReservationRepository reservationRepository;

    public final List<ReservationEntity> listAllOwnerProperties(final Long propertyId) {
        return reservationRepository.findByPropertyId(propertyId);
    }

    public ReservationEntity bookProperty(final RegisterReservationRequest request) {
        if (request.getCheckIn().isAfter(request.getCheckOut())) {
            throw new InvalidParameterException("check in cannot be greater than check out");
        }

        final Long propertyId = request.getPropertyId();

        final Optional<ReservationEntity> reservationOptional = reservationRepository
                .findByPropertyIdAndCheckInBetweenOrCheckOutBetween(propertyId, request.getCheckIn(), request.getCheckOut(), request.getCheckIn(), request.getCheckOut());

        if (reservationOptional.isPresent()) {
            final ReservationEntity currentReservation = reservationOptional.get();

            if (currentReservation.getBlockedByOwner()) {
                throw new InvalidParameterException("property owner blocked new reservations in this date range");
            } else {
                throw new InvalidParameterException("this property is already booked in this period");
            }

        }

        final Optional<PropertyEntity> propertyOptional = propertyRepository.findById(propertyId);
        if (propertyOptional.isEmpty()) {
            throw new InvalidParameterException("property was not found");
        }

        final List<PersonEntity> guests;
        if (!request.getBlockedByOwner()) {
            guests = personRepository.findByTaxIdIn(request.getGuests());
            if (guests.size() != request.getGuests().size()) {
                throw new InvalidParameterException("not al guests are registered");
            }
        } else {
            guests = List.of();
        }


        final ReservationEntity reservation = ReservationEntity.builder()
                .blockedByOwner(request.getBlockedByOwner())
                .property(propertyOptional.get())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .guests(guests)
                .build();

        return reservationRepository.save(reservation);
    }

}
