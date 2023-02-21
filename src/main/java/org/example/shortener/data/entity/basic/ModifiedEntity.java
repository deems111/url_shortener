package org.example.shortener.data.entity.basic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class ModifiedEntity {
    @Column
    private LocalDateTime created;

    @Column
    private LocalDateTime modified;

    @PrePersist
    public void create() {
        this.created = LocalDateTime.now();
    }

    @PreUpdate
    public void update() {
        this.modified = LocalDateTime.now();
    }

}
