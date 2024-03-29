package org.example.shortener.data.request;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class ShortenerRequest {

    @NotNull(message = "User Id is mandatory")
    private UUID userId;

    @NotBlank(message = "Url is mandatory")
    @URL(message = "Not a url")
    private String url;

    @Size(max = 8)
    private String shortUrl;

}
