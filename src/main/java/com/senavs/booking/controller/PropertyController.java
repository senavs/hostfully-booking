package com.senavs.booking.controller;

import com.senavs.booking.model.dto.PropertyDto;
import com.senavs.booking.model.dto.PropertyWithoutOwnerDto;
import com.senavs.booking.model.entity.PropertyEntity;
import com.senavs.booking.service.PropertyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/property")
@RequiredArgsConstructor
public class PropertyController {

    private final ModelMapper modelMapper;
    private final PropertyService propertyService;

    @GetMapping("{ownerTaxId}")
    public ResponseEntity<List<PropertyWithoutOwnerDto>> listAllOwnerProperties(@Valid @NotNull @NotBlank @PathVariable final String ownerTaxId) {
        final List<PropertyWithoutOwnerDto> properties = propertyService.listAllOwnerProperties(ownerTaxId)
                .stream()
                .map(propertyEntity -> modelMapper.map(propertyEntity, PropertyWithoutOwnerDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(properties, OK);
    }

    @PostMapping
    public ResponseEntity<PropertyDto> registerProperty(@Valid @RequestBody final PropertyDto propertyDto) {
        final PropertyEntity propertyEntity = modelMapper.map(propertyDto, PropertyEntity.class);
        final PropertyEntity savedPropertyEntity = propertyService.createProperty(propertyEntity);
        final PropertyDto savedPropertyDto = modelMapper.map(savedPropertyEntity, PropertyDto.class);
        return new ResponseEntity<>(savedPropertyDto, CREATED);
    }
}
