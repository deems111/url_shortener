package org.example.shortener.data.request;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

@Data
public class ShortenerRequest {

    @Parameter(name = "userId", description = "User id", example = "5461685e-b06b-11ed-afa1-0242ac120002", required = true)
    private UUID userId;

    @URL
    @Parameter(name = "url", description = "Valid url", example = "https://www.baeldung.com/", required = true)
    private String url;
}
