package org.example.shortener.event.publisher;

import lombok.AllArgsConstructor;
import org.example.shortener.data.entity.Shortener;
import org.example.shortener.enums.EventType;
import org.example.shortener.event.ShortenerModifyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShortenerEventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    public void modifyUrl(Shortener shortener, EventType type) {
        var shortenerModifyEvent = new ShortenerModifyEvent(this, shortener, type);
        applicationEventPublisher.publishEvent(shortenerModifyEvent);
    }

}
