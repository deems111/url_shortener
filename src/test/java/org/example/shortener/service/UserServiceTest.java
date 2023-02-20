package org.example.shortener.service;

import org.example.shortener.ApplicationTest;
import org.example.shortener.data.request.UserAddRequest;
import org.example.shortener.exception.DuplicateException;
import org.example.shortener.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static org.example.shortener.service.util.Utility.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class UserServiceTest extends ApplicationTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Test throws duplicate exception")
    public void testDuplicateException() {
        var request = new UserAddRequest();
        request.setEmail(DEFAULT_EMAIL);
        request.setName(DEFAULT_NAME);
        var throwable = assertThrows(DuplicateException.class, () -> userService.addUser(Locale.US, request));
        assertEquals(throwable.getMessage(), "User with email = user@gmail.com already exists");
    }

    @Test
    @DisplayName("Test successfully user added")
    public void testAddUser() {
        var existingCount = userRepository.findAll().size();
        var request = new UserAddRequest();
        request.setEmail(DEFAULT_UNIQUE_EMAIL);
        request.setName(DEFAULT_NAME);
        var addedUser = userService.addUser(Locale.US, request);
        var newCount = userRepository.findAll().size();
        assertEquals(1, newCount - existingCount);

        var getUser = userService.getUser(Locale.US, addedUser.getId());
        assertNotNull(getUser);
        assertEquals(addedUser, getUser);
    }

}
