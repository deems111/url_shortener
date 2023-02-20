package org.example.shortener.mapper;

import org.example.shortener.data.entity.Shortener;
import org.example.shortener.data.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.example.shortener.service.util.Utility.DEFAULT_SHORT_URL;
import static org.example.shortener.service.util.Utility.DEFAULT_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ShortenerMapperTest {

    @Autowired
    ShortenerMapper shortenerMapper;

    @Test
    @DisplayName("Check shortenerMapper method to DTO")
    public void testShortenerMapper() {
        var shortenerEntity = new Shortener(DEFAULT_SHORT_URL, DEFAULT_URL, new User());
        var result = shortenerMapper.toDto(shortenerEntity);
        assertEquals(result.getShortUrl(), DEFAULT_SHORT_URL);
        assertEquals(result.getUrl(), DEFAULT_URL);
    }

}
