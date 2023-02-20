package org.example.shortener.repository;

import org.example.shortener.data.entity.Shortener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShortenerRepository extends JpaRepository<Shortener, UUID> {
    @Query("select sh from Shortener sh where sh.createdAt > :min and sh.createdAt < :max")
    List<Shortener> findAllByCreatedAt(LocalDateTime min, LocalDateTime max);

    @Query("select sh from Shortener sh where sh.shortUrl = :shortUrl")
    Optional<Shortener> findUrlByShortUrlEquals(String shortUrl);
}
