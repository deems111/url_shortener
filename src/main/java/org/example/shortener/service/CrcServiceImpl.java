package org.example.shortener.service;

import org.example.shortener.aop.annotation.LogExecutionTime;
import org.springframework.stereotype.Service;

import java.util.zip.CRC32;

/**
 * Service computes CRC32 data checksum for shortening String (byte[])
 */
@Service
public class CrcServiceImpl implements CrcService {
    @Override
    @LogExecutionTime
    public String toHexString(byte[] bytes) {
        CRC32 crc = new CRC32();
        crc.update(bytes);
        return Long.toHexString(crc.getValue());
    }
}
