package org.example.shortener.mapper;

import org.example.shortener.data.dto.ClickTrackingDto;
import org.example.shortener.data.entity.ClickTracking;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClickTrackingMapper {

    List<ClickTrackingDto> listToDto(List<ClickTracking> entity);
}
