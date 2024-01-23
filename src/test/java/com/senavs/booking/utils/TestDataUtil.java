package com.senavs.booking.utils;

import com.senavs.booking.model.entity.PersonEntity;
import com.senavs.booking.model.entity.PropertyEntity;
import com.senavs.booking.model.entity.ReservationEntity;
import com.senavs.booking.model.request.RegisterPropertyRequest;
import com.senavs.booking.model.request.RegisterReservationRequest;

import java.time.LocalDate;
import java.util.List;

public class TestDataUtil {

    public static PersonEntity createTestPersonEntity() {
        return PersonEntity.builder()
                .taxId("5550123")
                .name("Fulano de Tal")
                .age(25)
                .build();
    }

    public static PropertyEntity createTestPropertyEntity() {
        return PropertyEntity.builder()
                .id(1L)
                .address("Some Random Place")
                .owner(createTestPersonEntity())
                .build();
    }

    public static ReservationEntity createTestReservationEntity() {
        return ReservationEntity.builder()
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now())
                .guests(List.of(createTestPersonEntity()))
                .isDeleted(false)
                .isBlockedByOwner(false)
                .property(createTestPropertyEntity())
                .build();
    }

    public static RegisterPropertyRequest createTestRegisterPropertyRequest(final String ownerTaxId) {
        return RegisterPropertyRequest.builder()
                .address("some random address")
                .ownerTaxId(ownerTaxId)
                .build();
    }

    public static RegisterReservationRequest createTestRequestFromReservationEntity(final ReservationEntity reservation) {
        return RegisterReservationRequest.builder()
                .blockedByOwner(reservation.getIsBlockedByOwner())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .propertyId(reservation.getProperty().getId())
                .guests(reservation.getGuests().stream().map(PersonEntity::getTaxId).toList())
                .build();
    }
}
