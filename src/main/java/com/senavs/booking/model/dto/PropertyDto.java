package com.senavs.booking.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.senavs.booking.model.entity.PersonEntity;
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
    private String address;
    private PersonEntity owner;
}
