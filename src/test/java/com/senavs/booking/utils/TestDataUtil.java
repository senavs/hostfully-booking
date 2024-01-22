package com.senavs.booking.utils;

import com.senavs.booking.model.entity.PersonEntity;
import com.senavs.booking.model.entity.PropertyEntity;

public class TestDataUtil {

    public static PropertyEntity createTestPropertyEntity() {
        return PropertyEntity.builder()
                .id(1L)
                .address("Some Random Place")
                .owner(PersonEntity.builder()
                        .taxId("5550123")
                        .name("Foo Bar")
                        .age(18)
                        .build())
                .build();
    }

    public static PropertyEntity createTestPropertyEntityWithoutOwner() {
        final PropertyEntity testPropertyEntity = TestDataUtil.createTestPropertyEntity();
        testPropertyEntity.setOwner(null);
        return testPropertyEntity;
    }
}
