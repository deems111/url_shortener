package org.example.shortener.data.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ClickTrackingDto {

    private UUID id;
    private String ip;
    private UUID shortenerId;
    private LocalDateTime created;
}
