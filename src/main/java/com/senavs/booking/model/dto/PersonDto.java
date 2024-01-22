package com.senavs.booking.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class PersonDto {
    @NotNull(message = "person taxId cannot be null")
    @NotEmpty(message = "person taxId cannot be empty")
    private String taxId;

    @NotNull(message = "person name cannot be null")
    @NotEmpty(message = "person name cannot be empty")
    private String name;

    @Min(value = 18, message = "person has be at last 18 years old to own a property")
    @NotNull(message = "person age cannot be null")
    private Integer age;
}
