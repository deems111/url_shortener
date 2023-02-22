package org.example.shortener.repository;

import org.example.shortener.data.entity.Shortener;
import org.example.shortener.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShortenerRepository extends JpaRepository<Shortener, UUID> {
    @Query("select sh from Shortener sh where sh.created > :min and sh.created < :max and sh.disabled = false")
    List<Shortener> findAllByCreated(LocalDateTime min, LocalDateTime max);

    @Query("select sh from Shortener sh where sh.shortUrl = :shortUrl and sh.disabled = false")
    Optional<Shortener> findUrlByShortUrlEquals(String shortUrl);

    @Transactional(propagation = Propagation.REQUIRED)
    @Modifying
    @Query("update Shortener sh set sh.disabled = true, sh.modified = : nowDate where sh.id = :id")
    void setDisabled(UUID id);

    Integer countAllByUserIsAndDisabled(User user, boolean disabled);

}
