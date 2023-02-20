package org.example.shortener.data.request;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UserRegistrationRequest {

    @Email
    @Parameter(name = "email", description = "User email, valid", example = "Nick@gmail.com", required = true)
    private String email;
}
