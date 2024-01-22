package com.senavs.booking.service;

import com.senavs.booking.model.entity.PersonEntity;
import com.senavs.booking.model.entity.PropertyEntity;
import com.senavs.booking.model.request.RegisterPropertyRequest;
import com.senavs.booking.repository.PersonRepository;
import com.senavs.booking.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PersonRepository personRepository;

    public final List<PropertyEntity> listAllOwnerProperties(final String ownerTaxId) {
        return propertyRepository.findByOwnerTaxId(ownerTaxId);
    }

    public final PropertyEntity createProperty(final RegisterPropertyRequest request) {
        final Optional<PersonEntity> ownerOptional = personRepository.findByTaxId(request.getOwnerTaxId());
        if (ownerOptional.isEmpty()) {
            throw new InvalidParameterException("owner does not exist");
        }

        final PropertyEntity property = PropertyEntity.builder()
                .address(request.getAddress())
                .owner(ownerOptional.get())
                .build();

        return propertyRepository.save(property);
    }

}
