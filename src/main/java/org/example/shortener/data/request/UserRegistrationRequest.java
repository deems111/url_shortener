package org.example.shortener.data.request;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UserRegistrationRequest {

    @Email(message = "Not a Email")
    private String email;
}
