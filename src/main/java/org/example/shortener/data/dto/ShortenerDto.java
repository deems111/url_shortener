package org.example.shortener.data.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShortenerDto {

    private String url;
    private String shortUrl;
    private LocalDateTime createdAt;
}
