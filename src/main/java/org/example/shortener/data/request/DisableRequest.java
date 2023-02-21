package org.example.shortener.data.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class DisableRequest {

    @NotNull(message = "User Id is mandatory")
    private UUID userId;

    @NotNull(message = "Id is mandatory")
    private UUID id;

}
