package org.example.shortener.mapper;

import org.example.shortener.data.dto.ShortenerEventDto;
import org.example.shortener.data.entity.ShortenerEvent;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShortenerEventMapper {

    List<ShortenerEventDto> listToDto(List<ShortenerEvent> entity);
}
