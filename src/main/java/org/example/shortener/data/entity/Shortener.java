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
@Table(name = "test_shortener")
@NoArgsConstructor
public class Shortener extends BasicEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "uuid-char")
    private UUID id;

    private String shortUrl;

    @Column(nullable = false)
    private String url;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Shortener(String shortUrl, String url, User user) {
        this.shortUrl = shortUrl;
        this.url = url;
        this.user = user;
    }
}
