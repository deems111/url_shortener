package org.example.shortener.service;

import org.example.shortener.data.request.UserAddRequest;
import org.example.shortener.data.dto.UserDto;

import java.util.Locale;
import java.util.UUID;

public interface UserService {

    UserDto addUser(Locale locale, UserAddRequest request);

    UserDto getUser(Locale locale, UUID userId);

}
