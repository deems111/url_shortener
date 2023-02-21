package org.example.shortener.event;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.shortener.data.entity.Shortener;
import org.example.shortener.enums.EventType;
import org.springframework.context.ApplicationEvent;

@Slf4j
@Getter
public class ShortenerModifyEvent extends ApplicationEvent {
    private final Shortener shortener;
    private final EventType type;

    public ShortenerModifyEvent(Object source, Shortener shortener, EventType type) {
        super(source);
        this.shortener = shortener;
        this.type = type;
        log.info("Event with type {} for shortener {} created", type, shortener);
    }

}
