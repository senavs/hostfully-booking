package com.senavs.booking.mapper;

import com.senavs.booking.model.dto.PropertyDto;
import com.senavs.booking.model.entity.PropertyEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropertyMapper implements Mapper<PropertyEntity, PropertyDto> {

    private final ModelMapper modelMapper;

    @Override
    public PropertyDto unmap(PropertyEntity propertyEntity) {
        return modelMapper.map(propertyEntity, PropertyDto.class);
    }

    @Override
    public PropertyEntity map(PropertyDto propertyDto) {
        return modelMapper.map(propertyDto, PropertyEntity.class);
    }
}
