package org.example.shortener.service;

import org.junit.jupiter.api.RepeatedTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CrcServiceTest {
    private static final int DEFAULT_RANDOM_MULTIPLIER = 500;

    @RepeatedTest(10)
    public void testLengthString() {
        var crcService = new CrcServiceImpl();
        var randomArray = getRandomBytes();
        var result = crcService.toHexString(randomArray);
        System.out.println("randomArray length =" + randomArray.length);
        System.out.println("result =" + result);
        assertTrue(result.length() > 0 && result.length() <= 8);
    }

    private byte[] getRandomBytes() {
        var arraySize = (int)Math.ceil(Math.random() * DEFAULT_RANDOM_MULTIPLIER);
        byte[] randomArray = new byte[arraySize];
        new Random().nextBytes(randomArray);
        return randomArray;
    }
}
