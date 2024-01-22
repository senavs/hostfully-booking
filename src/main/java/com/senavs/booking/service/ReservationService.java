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
        final Long propertyId = request.getPropertyId();

        final Optional<PropertyEntity> propertyOptional = propertyRepository.findById(propertyId);
        if (propertyOptional.isEmpty()) {
            throw new IllegalArgumentException("property was not found");
        }

        final List<PersonEntity> guests = personRepository.findByTaxIdIn(request.getGuests());
        if (guests.size() != request.getGuests().size()) {
            throw new IllegalArgumentException("not al guests are registered");
        }

        final ReservationEntity reservation = ReservationEntity.builder()
                .blockedByOwner(request.getBlockedByOwner())
                .property(propertyOptional.get())
                .guests(guests)
                .build();

        return reservationRepository.save(reservation);
    }

}
