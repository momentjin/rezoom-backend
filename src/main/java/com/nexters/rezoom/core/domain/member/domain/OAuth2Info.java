package com.nexters.rezoom.core.domain.member.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by momentjin@gmail.com on 2019-12-19
 * Github : http://github.com/momentjin
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2Info {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PK;

    @Column(name = "provider_type")
    private String providerType;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "expires_it")
    private LocalDateTime expiresIn;

    public OAuth2Info(String providerType, String accessToken, LocalDateTime expiresIn) {
        this.providerType = providerType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public void updateInfo(String accessToken, LocalDateTime expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }
}
