package com.senavs.booking.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class RegisterReservationRequest {

    private Boolean blockedByOwner = false;

    @NotNull(message = "property id cannot be null")
    private Long propertyId;

    @NotNull(message = "check in cannot be null")
    private LocalDate checkIn;

    @NotNull(message = "check out cannot be null")
    private LocalDate checkOut;

    @NotNull(message = "guests tax id cannot be null")
    @NotEmpty(message = "guests tax id cannot be empty")
    private List<String> guests;
}
