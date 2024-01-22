package com.senavs.booking.service;

import com.senavs.booking.model.entity.PersonEntity;
import com.senavs.booking.model.entity.PropertyEntity;
import com.senavs.booking.repository.PersonRepository;
import com.senavs.booking.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PersonRepository personRepository;

    public final PropertyEntity createProperty(final PropertyEntity propertyEntity) {
        final PersonEntity ownerEntity = personRepository.findByTaxId(propertyEntity.getOwner().getTaxId());
        if (ownerEntity != null) {
            propertyEntity.setOwner(ownerEntity);
        }

        return propertyRepository.save(propertyEntity);
    }

}