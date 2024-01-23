package com.senavs.booking.utils;

import com.senavs.booking.model.entity.PersonEntity;
import com.senavs.booking.model.entity.PropertyEntity;
import com.senavs.booking.model.request.RegisterPropertyRequest;

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

    public static RegisterPropertyRequest createTestRegisterPropertyRequest(final String ownerTaxId) {
        return RegisterPropertyRequest.builder()
                .address("some random address")
                .ownerTaxId(ownerTaxId)
                .build();
    }
}
