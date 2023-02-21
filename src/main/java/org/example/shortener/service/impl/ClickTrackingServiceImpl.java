package org.example.shortener.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.shortener.aop.annotation.LogAll;
import org.example.shortener.data.dto.ClickTrackingDto;
import org.example.shortener.data.entity.ClickTracking;
import org.example.shortener.enums.Sorting;
import org.example.shortener.exception.NotFoundException;
import org.example.shortener.mapper.ClickTrackingMapper;
import org.example.shortener.repository.ClickTrackingRepository;
import org.example.shortener.service.ClickTrackingService;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.example.shortener.util.Constants.MESSAGE_URL_NOT_FOUND;


/**
 * Service for managing clicks
 */
@Service
@RequiredArgsConstructor
public class ClickTrackingServiceImpl implements ClickTrackingService {

    private final ClickTrackingRepository clickTrackingRepository;
    private final ClickTrackingMapper mapper;
    private final MessageSource messageSource;

    private static final String FIELD_CREATED = "created";

    @Override
    @Transactional
    @LogAll
    public void saveClickTracking(UUID shortenerId, String ip) {
        var clickTracking = new ClickTracking();
        clickTracking.setShortenerId(shortenerId);
        clickTracking.setIp(ip);
        clickTrackingRepository.save(clickTracking);
    }

    @Override
    @LogAll
    public List<ClickTrackingDto> findByShortenerId(Locale locale, UUID shortenerId, int pageNum, int pageSize, Sorting sort) {
        var sorting = Sort.by(sort == Sorting.ASC ? Sort.Order.asc(FIELD_CREATED) : Sort.Order.desc(FIELD_CREATED));
        var pageable = PageRequest.of(pageNum, pageSize, sorting);
        var clickTrackingList = clickTrackingRepository.findAllByShortenerIdIs(shortenerId, pageable);
        if (clickTrackingList.isEmpty()) {
            throw new NotFoundException(messageSource.getMessage(MESSAGE_URL_NOT_FOUND,
                    new Object[]{shortenerId}, locale));
        }
        return mapper.listToDto(clickTrackingList);
    }
}
