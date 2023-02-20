package org.example.shortener.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.shortener.aop.annotation.LogException;
import org.example.shortener.aop.annotation.LogExecutionTime;
import org.example.shortener.data.dto.ShortUrlDto;
import org.example.shortener.data.entity.Shortener;
import org.example.shortener.data.entity.User;
import org.example.shortener.data.request.ShortenerRequest;
import org.example.shortener.exception.NotFoundException;
import org.example.shortener.mapper.ShortenerMapper;
import org.example.shortener.repository.ShortenerRepository;
import org.example.shortener.repository.UserRepository;
import org.example.shortener.service.CrcService;
import org.example.shortener.service.ShortenerService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


/**
 * Service for managing url (short URL / get Original URL / get by query)
 */
@Component
@RequiredArgsConstructor
public class ShortenerServiceImpl implements ShortenerService {

    private final CrcService crcService;
    private final ShortenerRepository shortenerRepository;
    private final UserRepository userRepository;
    private final ShortenerMapper shortenerMapper;
    private final MessageSource messageSource;

    @Override
    @Transactional
    @LogExecutionTime
    @LogException
    public ShortUrlDto addUrl(Locale locale, ShortenerRequest request) {
        String originalUrl = request.getUrl();
        User user = getUserOrThrow(locale, request.getUserId());
        String shortUrl = crcService.toHexString(originalUrl.getBytes());
        shortenerRepository.save(getShortener(user, originalUrl, shortUrl));
        return new ShortUrlDto(originalUrl, shortUrl);
    }

    @Override
    @LogException
    public ShortUrlDto getOriginalUrl(Locale locale, UUID userId, String shortUrl) {
        var shortUrlDb = shortenerRepository.findUrlByShortUrlEquals(shortUrl)
                .orElseThrow(() ->
                        new NotFoundException(messageSource.getMessage("error.url.notfound",
                                new Object[]{shortUrl}, locale)));
        return new ShortUrlDto(shortUrlDb.getUrl(), shortUrlDb.getShortUrl());
    }

    @Override
    @LogExecutionTime
    @LogException
    public List<ShortUrlDto> findUrlAllByUser(Locale locale, UUID userId) {
        List<ShortUrlDto> resultList = new ArrayList<>();
        User user = getUserOrThrow(locale, userId);
        user.getShorteners().forEach(entity -> resultList.add(shortenerMapper.toDto(entity)));
        return resultList;
    }

    @Override
    @LogExecutionTime
    @LogException
    public List<ShortUrlDto> findUrlAllByPeriod(Locale locale, LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<ShortUrlDto> resultList = new ArrayList<>();
        shortenerRepository.findAllByCreatedAt(dateFrom, dateTo)
                .forEach(entity -> resultList.add(shortenerMapper.toDto(entity)));
        return resultList;
    }

    private Shortener getShortener(User user, String originalUrl, String shortUrl) {
        return new Shortener(shortUrl, originalUrl, user);
    }

    private User getUserOrThrow(Locale locale, UUID userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(messageSource.getMessage("error.user.notfound", new Object[]{userId}, locale)));
    }
}
