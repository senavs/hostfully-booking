package com.senavs.booking.service;

import com.senavs.booking.model.entity.PersonEntity;
import com.senavs.booking.model.request.RegisterPersonRequest;
import com.senavs.booking.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public final PersonEntity registerPerson(final RegisterPersonRequest request) {
        final Optional<PersonEntity> personOptional = personRepository.findByTaxId(request.getTaxId());
        if (personOptional.isPresent()) {
            throw new InvalidParameterException("person already exists");
        }

        final PersonEntity person = PersonEntity.builder()
                .taxId(request.getTaxId())
                .name(request.getName())
                .age(request.getAge())
                .build();

        return personRepository.save(person);
    }

}
