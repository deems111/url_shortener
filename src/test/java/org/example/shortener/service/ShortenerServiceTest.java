package org.example.shortener.service;

import org.example.shortener.ApplicationTest;
import org.example.shortener.data.request.ShortenerRequest;
import org.example.shortener.repository.ShortenerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Locale;

import static org.example.shortener.service.util.Utility.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
public class ShortenerServiceTest extends ApplicationTest {

    @Autowired
    private ShortenerService shortenerService;
    @Autowired
    private ShortenerRepository shortenerRepository;

    @Test
    @DisplayName("Test short url added")
    public void testAddShortUrl() {
        var existingCount = shortenerRepository.findAll().size();
        var adderUrl = shortenerService.addUrl(Locale.US, getRequest());
        var newCount = shortenerRepository.findAll().size();
        assertEquals(1, newCount - existingCount);

        var getRequest = shortenerService.getOriginalUrl(Locale.US, DEFAULT_USER_UUID, adderUrl.getShortUrl());
        assertNotNull(getRequest);
        assertEquals(DEFAULT_URL, getRequest.getUrl());
        assertEquals(adderUrl.getShortUrl(), getRequest.getShortUrl());
    }

    @Test
    @DisplayName("Test short with query by user found")
    public void testQueryShortUrl() {
        var getShorteners = shortenerService.findUrlAllByUser(Locale.US, DEFAULT_USER_UUID);
        assertNotNull(getShorteners);
        assertEquals(1, getShorteners.stream().filter(shrtn -> shrtn.getUrl().equals(DEFAULT_DB_URL)).count());
    }

    @Test
    @DisplayName("Test get by period short url")
    public void testQueryDateShortUrl() {
        var includeDate = LocalDateTime.of(2023, Month.FEBRUARY, 19, 17, 56, 59);
        var getShorteners = shortenerService.findUrlAllByPeriod(Locale.US, includeDate, LocalDateTime.now());
        assertEquals(1, getShorteners.stream().filter(shrtn -> shrtn.getUrl().equals(DEFAULT_DB_URL)).count());

        var now = LocalDateTime.now().minusSeconds(1);
        shortenerService.addUrl(Locale.US, getRequest());
        getShorteners = shortenerService.findUrlAllByPeriod(Locale.US, includeDate, LocalDateTime.now());
        assertEquals(1, getShorteners.stream().filter(shrtn -> shrtn.getUrl().equals(DEFAULT_URL)).count());
        assertEquals(1, getShorteners.stream().filter(shrtn -> shrtn.getUrl().equals(DEFAULT_DB_URL)).count());

        getShorteners = shortenerService.findUrlAllByPeriod(Locale.US, includeDate, now);
        assertEquals(0, getShorteners.stream().filter(shrtn -> shrtn.getUrl().equals(DEFAULT_URL)).count());
    }

    private ShortenerRequest getRequest() {
        var request = new ShortenerRequest();
        request.setUrl(DEFAULT_URL);
        request.setUserId(DEFAULT_USER_UUID);
        return request;
    }

}
