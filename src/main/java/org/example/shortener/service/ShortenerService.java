package org.example.shortener.service;

import org.example.shortener.data.request.ShortenerRequest;
import org.example.shortener.data.dto.ShortUrlDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface ShortenerService {

    ShortUrlDto addUrl(Locale locale, ShortenerRequest request);

    ShortUrlDto getOriginalUrl(Locale locale, UUID userId, String shortUrl);

    List<ShortUrlDto> findUrlAllByUser(Locale locale, UUID userId);

    List<ShortUrlDto> findUrlAllByPeriod(Locale locale, LocalDateTime dateFrom, LocalDateTime dateTo);

}
