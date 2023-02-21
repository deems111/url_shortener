package org.example.shortener.service;

import org.example.shortener.ApplicationTest;
import org.example.shortener.enums.EventType;
import org.example.shortener.event.ShortenerModifyEvent;
import org.example.shortener.event.listener.ShortenerEventListener;
import org.example.shortener.repository.ShortenerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

import static org.example.shortener.service.util.Utility.DEFAULT_SHORTENER_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ShortenerPublisherTest extends ApplicationTest {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ShortenerRepository shortenerRepository;

    @MockBean
    private ShortenerEventListener shortenerEventListener;

    @Test
    @DisplayName("Test the event published on add url")
    public void checkEventPublished() {
        var shortener = shortenerRepository.findById(DEFAULT_SHORTENER_ID).get();
        var event = new ShortenerModifyEvent(this, shortener, EventType.MODIFIED);

        applicationEventPublisher.publishEvent(event);
        verify(shortenerEventListener, times(1)).saveShortenerEvent(any());
    }


}
