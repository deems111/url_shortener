package org.example.shortener.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.shortener.aop.annotation.LogAll;
import org.example.shortener.data.dto.ShortenerEventDto;
import org.example.shortener.data.entity.Shortener;
import org.example.shortener.data.entity.ShortenerEvent;
import org.example.shortener.enums.EventType;
import org.example.shortener.exception.NotFoundException;
import org.example.shortener.mapper.ShortenerEventMapper;
import org.example.shortener.repository.ShortenerEventRepository;
import org.example.shortener.service.ShortenerEventService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.example.shortener.util.Constants.MESSAGE_EVENT_NOT_FOUND;


/**
 * Service for managing Events of modifying / creating url
 */
@Service
@RequiredArgsConstructor
public class ShortenerEventServiceImpl implements ShortenerEventService {

    private final ShortenerEventRepository eventRepository;
    private final ShortenerEventMapper mapper;
    private final MessageSource messageSource;

    @Transactional
    @Override
    @LogAll
    public void saveEvent(Shortener shortener, EventType type) {
        var shortenerEvent = new ShortenerEvent();
        shortenerEvent.setType(type);
        shortenerEvent.setShortener(shortener);
        eventRepository.save(shortenerEvent);
    }

    @Override
    @LogAll
    public List<ShortenerEventDto> findByShortenerId(Locale locale, UUID shortenerId) {
        var shortenerEvents = eventRepository.findAllByShortener_Id(shortenerId);
        if (shortenerEvents.isEmpty()) {
            throw new NotFoundException(messageSource.getMessage(MESSAGE_EVENT_NOT_FOUND,
                    new Object[]{shortenerId}, locale));
        }
        return mapper.listToDto(shortenerEvents);
    }
}
