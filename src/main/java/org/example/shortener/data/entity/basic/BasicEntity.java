package org.example.shortener.data.entity.basic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BasicEntity {
    @Column
    private LocalDateTime createdAt;

    @PrePersist
    public void create() {
        this.createdAt = LocalDateTime.now();
    }

}
