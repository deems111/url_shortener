package org.example.shortener.repository;

import org.example.shortener.data.entity.ClickTracking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClickTrackingRepository extends JpaRepository<ClickTracking, UUID> {

    List<ClickTracking> findAllByShortenerIdIs(UUID shortenerId, Pageable pageable);
}
