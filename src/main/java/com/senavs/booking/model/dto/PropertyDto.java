package com.senavs.booking.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties
public class PropertyDto {
    private Long id;

    @NotNull(message = "property address cannot be null")
    @NotEmpty(message = "property address cannot be empty")
    private String address;

    @Valid
    @NotNull(message = "property owner cannot be null")
    private PersonDto owner;
}
