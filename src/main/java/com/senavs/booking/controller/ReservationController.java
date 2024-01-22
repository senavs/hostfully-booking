package com.senavs.booking.controller;

import com.senavs.booking.model.entity.ReservationEntity;
import com.senavs.booking.model.request.RegisterReservationRequest;
import com.senavs.booking.model.response.ListAllOwnerPropertiesResponse;
import com.senavs.booking.model.response.RegisterReservationResponse;
import com.senavs.booking.service.ReservationService;
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
@RequiredArgsConstructor
public class ReservationController {

    private final ModelMapper modelMapper;
    private final ReservationService reservationService;

    @GetMapping("/reservation/{propertyId}")
    public ResponseEntity<List<ReservationEntity>> listAllReservations(@Valid @NotNull @PathVariable final Long propertyId) {
        final List<ReservationEntity> reservations = reservationService.listAllOwnerProperties(propertyId)
                .stream()
                .map(propertyEntity -> modelMapper.map(propertyEntity, ReservationEntity.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(reservations, OK);
    }

    @PostMapping("/reservation")
    public ResponseEntity<ReservationEntity> bookProperty(@Valid @RequestBody final RegisterReservationRequest request) {
        final ReservationEntity reservation = reservationService.bookProperty(request);
        return new ResponseEntity<>(ReservationEntity.builder().build(), CREATED);
    }
}
