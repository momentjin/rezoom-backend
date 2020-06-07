package com.nexters.rezoom.core.domain.member.domain;

import com.nexters.rezoom.core.domain.notification.domain.NotificationSetting;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by momentjin@gmail.com on 2020-06-07
 * Github : http://github.com/momentjin
 */
@Getter
@MappedSuperclass
@EqualsAndHashCode(of = {"PK"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PK;

    @Column(name = "user_name", unique = true)
    protected String username;

    @Setter
    @Column(name = "name")
    protected String name;

    @Setter
    @Column(name = "password")
    protected String password;

    @Setter
    @Column(name = "motto")
    protected String motto;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    public Account(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public Account(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public void updateInfo(String name, String motto) {
        this.name = name;
        this.motto = motto;
    }
}
