package com.senavs.booking.controller;

import com.senavs.booking.model.entity.PropertyEntity;
import com.senavs.booking.model.request.RegisterPropertyRequest;
import com.senavs.booking.model.response.ListAllOwnerPropertiesResponse;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class PropertyController {

    private final ModelMapper modelMapper;
    private final PropertyService propertyService;

    @GetMapping("/property/{ownerTaxId}")
    public ResponseEntity<List<ListAllOwnerPropertiesResponse>> listAllOwnerProperties(@Valid @NotNull @NotBlank @PathVariable final String ownerTaxId) {
        final List<ListAllOwnerPropertiesResponse> properties = propertyService.listAllOwnerProperties(ownerTaxId)
                .stream()
                .map(propertyEntity -> modelMapper.map(propertyEntity, ListAllOwnerPropertiesResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(properties, OK);
    }

    @PostMapping("/property")
    public ResponseEntity<PropertyEntity> registerProperty(@Valid @RequestBody final RegisterPropertyRequest request) {
        final PropertyEntity propertyEntity = propertyService.createProperty(request);
        return new ResponseEntity<>(propertyEntity, CREATED);
    }
}
