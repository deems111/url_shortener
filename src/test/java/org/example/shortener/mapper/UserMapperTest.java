package org.example.shortener.mapper;

import org.example.shortener.data.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.example.shortener.service.util.Utility.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    @DisplayName("Check userMapper method to DTO")
    public void testUserMapper() {
        var userEntity = getUser();
        var result = userMapper.toDto(userEntity);
        assertEquals(result.getId(), DEFAULT_USER_UUID);
        assertEquals(result.getName(), DEFAULT_NAME);
        assertEquals(result.getEmail(), DEFAULT_EMAIL);
    }

    private User getUser() {
        var user = new User(DEFAULT_EMAIL, DEFAULT_NAME);
        user.setId(DEFAULT_USER_UUID);
        return user;
    }


}
