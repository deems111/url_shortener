package org.example.shortener.data.dto;

import lombok.Data;
import org.example.shortener.enums.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ShortenerEventDto {

    private UUID id;
    private EventType type;
    private LocalDateTime created;
}
