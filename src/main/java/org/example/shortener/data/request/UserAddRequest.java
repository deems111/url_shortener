package org.example.shortener.data.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserAddRequest {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 5, max = 16, message = "Name should contains 5 - 16 symbols")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Not a Email")
    private String email;

}
