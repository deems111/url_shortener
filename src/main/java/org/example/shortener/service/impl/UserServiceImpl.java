package org.example.shortener.service.impl;

import lombok.AllArgsConstructor;
import org.example.shortener.aop.annotation.LogAll;
import org.example.shortener.aop.annotation.LogException;
import org.example.shortener.aop.annotation.LogExecutionTime;
import org.example.shortener.data.dto.UserDto;
import org.example.shortener.data.entity.User;
import org.example.shortener.data.request.UserAddRequest;
import org.example.shortener.exception.DuplicateException;
import org.example.shortener.exception.NotFoundException;
import org.example.shortener.mapper.UserMapper;
import org.example.shortener.repository.UserRepository;
import org.example.shortener.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.example.shortener.util.Constants.MESSAGE_USER_DUPLICATE;
import static org.example.shortener.util.Constants.MESSAGE_USER_NOT_FOUND;

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
    @LogAll
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
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage(MESSAGE_USER_NOT_FOUND,
                        new Object[]{userId}, locale)));
        return userMapper.toDto(user);
    }

    @Override
    @LogExecutionTime
    public List<String> getUserEmails(Locale locale, int pageNum, int pageSize) {
        var pageable = PageRequest.of(pageNum, pageSize);
        return userRepository.getEmails(pageable);
    }

    private void checkDuplicateUser(Locale locale, String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateException(messageSource.getMessage(MESSAGE_USER_DUPLICATE,
                    new Object[]{email}, locale));
        }
    }
}
