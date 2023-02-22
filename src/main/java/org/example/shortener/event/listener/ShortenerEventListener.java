package org.example.shortener.event.listener;

import lombok.AllArgsConstructor;
import org.example.shortener.event.ShortenerModifyEvent;
import org.example.shortener.service.ShortenerEventService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShortenerEventListener {

    private final ShortenerEventService shortenerEventService;

    @EventListener
    public void saveShortenerEvent(ShortenerModifyEvent event) {
        shortenerEventService.saveEvent(event.getShortener(), event.getType());
    }
}
