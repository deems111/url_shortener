package org.example.shortener.service.impl;

import lombok.AllArgsConstructor;
import org.example.shortener.aop.annotation.LogException;
import org.example.shortener.aop.annotation.LogExecutionTime;
import org.example.shortener.data.request.UserAddRequest;
import org.example.shortener.data.dto.UserDto;
import org.example.shortener.data.entity.User;
import org.example.shortener.exception.DuplicateException;
import org.example.shortener.exception.NotFoundException;
import org.example.shortener.mapper.UserMapper;
import org.example.shortener.repository.UserRepository;
import org.example.shortener.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;

/**
 * Service for managing user (add / get)
 * Deletion of a user not supported
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MessageSource messageSource;

    @Override
    @LogExecutionTime
    @LogException
    public UserDto addUser(Locale locale, UserAddRequest request) {
        checkDuplicateUser(locale, request.getEmail());
        var user = new User(request.getEmail(), request.getName());
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    @LogException
    public UserDto getUser(Locale locale, UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("error.user.notfound",
                        new Object[]{userId}, locale)));
        return userMapper.toDto(user);
    }

    private void checkDuplicateUser(Locale locale, String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateException(messageSource.getMessage("error.user.duplicate",
                    new Object[]{email}, locale));
        }
    }
}
