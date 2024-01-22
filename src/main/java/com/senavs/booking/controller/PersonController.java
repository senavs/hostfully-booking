package com.senavs.booking.controller;

import com.senavs.booking.model.entity.PersonEntity;
import com.senavs.booking.model.request.RegisterPersonRequest;
import com.senavs.booking.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping("/person")
    public ResponseEntity<PersonEntity> registerProperty(@Valid @RequestBody final RegisterPersonRequest request) {
        final PersonEntity person = personService.registerPerson(request);
        return new ResponseEntity<>(person, CREATED);
    }
}
