package org.example.shortener.data.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class ModifyRequest {

    @NotNull(message = "User Id is mandatory")
    private UUID userId;

    @NotNull(message = "Shortener Id is mandatory")
    private UUID shortenerId;

    @Size(min = 3, max = 7, message = "Short URL should contain 3-7 symbols")
    private String shortUrl;

}
