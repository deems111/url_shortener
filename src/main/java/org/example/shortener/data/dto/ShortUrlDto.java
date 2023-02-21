package org.example.shortener.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlDto {

    private UUID id;

    private String url;

    private String shortUrl;
}
