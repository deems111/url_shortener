package org.example.shortener.service;

import org.example.shortener.data.dto.ClickTrackingDto;
import org.example.shortener.enums.Sorting;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface ClickTrackingService {

    void saveClickTracking(UUID shortenerId, String ip);

    List<ClickTrackingDto> findByShortenerId(Locale locale, UUID shortenerId, int pageNum, int pageSize, Sorting sort);
}
