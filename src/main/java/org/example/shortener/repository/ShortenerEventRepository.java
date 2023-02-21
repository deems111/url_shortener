package org.example.shortener.repository;

import org.example.shortener.data.entity.ShortenerEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShortenerEventRepository extends JpaRepository<ShortenerEvent, UUID> {

    List<ShortenerEvent> findAllByShortener_Id(UUID shortenerId);
}
