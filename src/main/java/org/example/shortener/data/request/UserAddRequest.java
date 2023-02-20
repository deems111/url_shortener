package org.example.shortener.data.request;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserAddRequest {

    @NotBlank
    @Size(min = 5, max = 16)
    @Parameter(name = "name", description = "User name, min 5 and max 16 symbols", example = "Nick", required = true)
    private String name;

    @NotBlank
    @Email
    @Parameter(name = "email", description = "User email, valid", example = "Nick@gmail.com", required = true)
    private String email;

}
