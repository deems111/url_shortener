package org.example.shortener.service;

import org.example.shortener.data.dto.ShortenerEventDto;
import org.example.shortener.data.entity.Shortener;
import org.example.shortener.enums.EventType;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface ShortenerEventService {

    void saveEvent(Shortener shortener, EventType type);

    List<ShortenerEventDto> findByShortenerId(Locale locale, UUID shortenerId);

}
