package org.example.shortener.service;

import org.example.shortener.ApplicationTest;
import org.example.shortener.controller.UserController;
import org.example.shortener.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.example.shortener.service.util.Utility.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
public class UserControllerTest extends ApplicationTest {
    @Autowired
    private UserController userController;

    @Test
    @DisplayName("Test throws exception when user not found")
    public void testNotFoundException() {
        assertThrows(NotFoundException.class, () -> userController.getUser("US", DEFAULT_UNIQUE_USER_UUID));
    }

    @Test
    @DisplayName("Test localized message when thrown exception - user not found")
    public void testNotFoundExceptionLocaleMessage() {
        var throwable = assertThrows(NotFoundException.class,
                () -> userController.getUser("US", DEFAULT_UNIQUE_USER_UUID));
        assertEquals(throwable.getMessage(), "User with UUID= 8c9438ab-0f45-4de6-b1c1-e33ada3e6577 not Found");

        throwable = assertThrows(NotFoundException.class,
                () -> userController.getUser("GE", DEFAULT_UNIQUE_USER_UUID));
        assertEquals(throwable.getMessage(), "Benutzer mit UUID= 8c9438ab-0f45-4de6-b1c1-e33ada3e6577 nicht gefunden");
    }
}
