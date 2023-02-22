package org.example.shortener.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shortener.data.entity.basic.BasicEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "test_click_tracking")
@NoArgsConstructor
public class ClickTracking extends BasicEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "uuid-char")
    private UUID id;

    @Column(nullable = false)
    @Type(type = "uuid-char")
    private UUID shortenerId;

    @Column(nullable = false)
    private String ip;

}
