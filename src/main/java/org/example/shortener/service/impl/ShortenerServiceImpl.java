package org.example.shortener.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.shortener.aop.annotation.LogAll;
import org.example.shortener.aop.annotation.LogException;
import org.example.shortener.data.dto.ShortUrlDto;
import org.example.shortener.data.entity.Shortener;
import org.example.shortener.data.entity.User;
import org.example.shortener.data.request.DisableRequest;
import org.example.shortener.data.request.ModifyRequest;
import org.example.shortener.data.request.ShortenerRequest;
import org.example.shortener.enums.EventType;
import org.example.shortener.event.publisher.ShortenerEventPublisher;
import org.example.shortener.exception.NotFoundException;
import org.example.shortener.mapper.ShortenerMapper;
import org.example.shortener.repository.ShortenerRepository;
import org.example.shortener.repository.UserRepository;
import org.example.shortener.service.ClickTrackingService;
import org.example.shortener.service.CrcService;
import org.example.shortener.service.ShortenerService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static org.example.shortener.util.Constants.MESSAGE_URL_NOT_FOUND;
import static org.example.shortener.util.Constants.MESSAGE_USER_NOT_FOUND;


/**
 * Service for managing url (short URL / get Original URL / get by query)
 */
@Service
@RequiredArgsConstructor
public class ShortenerServiceImpl implements ShortenerService {

    private final CrcService crcService;
    private final ShortenerRepository shortenerRepository;
    private final UserRepository userRepository;
    private final ShortenerMapper shortenerMapper;
    private final MessageSource messageSource;
    private final ShortenerEventPublisher publisher;
    private final ClickTrackingService clickTrackingService;

    @Override
    @Transactional
    @LogAll
    public ShortUrlDto addUrl(Locale locale, ShortenerRequest request) {
        var originalUrl = request.getUrl();
        var user = getUserOrThrow(locale, request.getUserId());
        var shortUrl = request.getShortUrl().isBlank() ? crcService.toHexString(originalUrl.getBytes())
                : request.getShortUrl();
        var shortener = shortenerRepository.save(getShortener(user, originalUrl, shortUrl));
        publishEvent(shortener, EventType.CREATED);
        return new ShortUrlDto(shortener.getId(), originalUrl, shortUrl);
    }

    @Override
    @LogException
    public ShortUrlDto getOriginalUrl(Locale locale, UUID userId, String shortUrl) {
        var shortener = shortenerRepository.findUrlByShortUrlEquals(shortUrl)
                .orElseThrow(() ->
                        new NotFoundException(messageSource.getMessage(MESSAGE_URL_NOT_FOUND,
                                new Object[]{shortUrl}, locale)));
        clickTrackingService.saveClickTracking(shortener.getId(), getIp());
        return new ShortUrlDto(shortener.getId(), shortener.getUrl(), shortener.getShortUrl());
    }

    @Override
    @LogAll
    public List<ShortUrlDto> findUrlAllByUser(Locale locale, UUID userId) {
        List<ShortUrlDto> resultList = new ArrayList<>();
        var user = getUserOrThrow(locale, userId);
        user.getShorteners().forEach(entity -> resultList.add(shortenerMapper.toDto(entity)));
        return resultList;
    }

    @Override
    @LogAll
    public List<ShortUrlDto> findUrlAllByPeriod(Locale locale, LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<ShortUrlDto> resultList = new ArrayList<>();
        shortenerRepository.findAllByCreated(dateFrom, dateTo)
                .forEach(entity -> resultList.add(shortenerMapper.toDto(entity)));
        return resultList;
    }

    @Override
    @Transactional
    @LogAll
    public void disableUrl(Locale locale, DisableRequest request) {
        var shortenerId = request.getId();
        var shortener = shortenerRepository.findById(shortenerId);
        if (shortener.isPresent()) {
            if (shortener.get().getUser().getId().equals(request.getUserId())) {
                shortenerRepository.setDisabled(shortenerId);
                publishEvent(shortener.get(), EventType.DISABLED);
                return;
            }
            throw new NotFoundException(messageSource.getMessage(MESSAGE_USER_NOT_FOUND,
                    new Object[]{request.getUserId()}, locale));
        }
        throw new NotFoundException(messageSource.getMessage(MESSAGE_URL_NOT_FOUND,
                new Object[]{request.getUserId()}, locale));
    }

    @Override
    @LogAll
    public Integer getCountOfUrlByUser(Locale locale, UUID userId, Boolean countDisabled) {
        var user = getUserOrThrow(locale, userId);
        return shortenerRepository.countAllByUserIsAndDisabled(user, countDisabled != null && countDisabled);
    }

    @Override
    public ShortUrlDto modifyShortUrl(Locale locale, ModifyRequest request) {
        var shortener = getShortenerOrThrow(locale, request.getShortenerId());
        shortener.setShortUrl(request.getShortUrl());
        shortenerRepository.save(shortener);
        publishEvent(shortener, EventType.MODIFIED);
        return new ShortUrlDto(shortener.getId(), shortener.getUrl(), shortener.getShortUrl());
    }

    private Shortener getShortener(User user, String originalUrl, String shortUrl) {
        return new Shortener(shortUrl, originalUrl, user);
    }

    private User getUserOrThrow(Locale locale, UUID userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(messageSource.getMessage(MESSAGE_USER_NOT_FOUND, new Object[]{userId}, locale)));
    }

    private Shortener getShortenerOrThrow(Locale locale, UUID shortenerId) {
        return shortenerRepository.findById(shortenerId).orElseThrow(() ->
                new NotFoundException(messageSource.getMessage(MESSAGE_URL_NOT_FOUND, new Object[]{shortenerId}, locale)));
    }

    @LogException
    private void publishEvent(Shortener shortener, EventType type) {
        publisher.modifyUrl(shortener, type);
    }

    private String getIp() {
        Random random = new Random();
        return random.nextInt(256) + "." + random.nextInt(256) + "." +
                random.nextInt(256) + "." + random.nextInt(256);
    }
}
