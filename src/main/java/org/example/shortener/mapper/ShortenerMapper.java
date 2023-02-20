package org.example.shortener.mapper;

import org.example.shortener.data.dto.ShortUrlDto;
import org.example.shortener.data.entity.Shortener;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShortenerMapper {

    ShortUrlDto toDto(Shortener entity);
}
