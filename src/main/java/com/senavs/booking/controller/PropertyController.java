package com.senavs.booking.controller;

import com.senavs.booking.mapper.PropertyMapper;
import com.senavs.booking.model.dto.PropertyDto;
import com.senavs.booking.model.entity.PropertyEntity;
import com.senavs.booking.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/property")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyMapper propertyMapper;
    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<PropertyDto> registerProperty(@Valid @RequestBody final PropertyDto propertyDto) {
        final PropertyEntity propertyEntity = propertyMapper.map(propertyDto);
        final PropertyEntity savedPropertyEntity = propertyService.createProperty(propertyEntity);
        final PropertyDto savedPropertyDto = propertyMapper.unmap(savedPropertyEntity);
        return new ResponseEntity<>(savedPropertyDto, CREATED);
    }
}
