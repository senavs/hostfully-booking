package com.senavs.booking.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPropertyRequest {
    @NotNull(message = "property address cannot be null")
    @NotEmpty(message = "property address cannot be empty")
    private String address;

    @Valid
    @NotNull(message = "property owner tax id cannot be null")
    private String ownerTaxId;
}
