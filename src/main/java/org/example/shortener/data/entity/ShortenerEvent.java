package org.example.shortener.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shortener.data.entity.basic.BasicEntity;
import org.example.shortener.enums.EventType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "test_shortener_event")
@NoArgsConstructor
public class ShortenerEvent extends BasicEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "uuid-char")
    private UUID id;

    @Column(nullable = false)
    private EventType type;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "shortener_id", referencedColumnName = "id")
    private Shortener shortener;

}
