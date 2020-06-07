package com.nexters.rezoom.core.domain.member.domain;

import com.nexters.rezoom.core.domain.notification.domain.NotificationSetting;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by momentjin@gmail.com on 2020-06-07
 * Github : http://github.com/momentjin
 */
@Getter
@Entity
@EqualsAndHashCode(of = {"PK"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

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

    @Setter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "oauth2InfoPK")
    private OAuth2Info oAuth2Info;

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


    public Account(String username, String name, OAuth2Info oAuth2Info) {
        this(username, name);
        this.oAuth2Info = oAuth2Info;
    }

    public void updateInfo(String name, String motto) {
        this.name = name;
        this.motto = motto;
    }

    public boolean isOAuth2Member() {
        if (this.oAuth2Info == null) {
            return false;
        }

        return true;
    }

    public void updateOAuth2Info(String name, String accessToken, LocalDateTime expiresIn) {
        if (!isOAuth2Member()) {
            throw new RuntimeException("OAuth2 Member가 아닙니다.");
        }

        this.name = name;
        this.oAuth2Info.updateInfo(accessToken, expiresIn);
    }
}
