package com.nexters.rezoom.core.domain.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * Created by momentjin@gmail.com on 2019-12-19
 * Github : http://github.com/momentjin
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2Member extends Account {

    @Column(name = "provider_type")
    private String providerType;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "expires_it")
    private LocalDateTime expiresIn;

    @Builder(builderClassName = "OAuth2MemberBuilder", builderMethodName = "oauth2MemberBuilder")
    public OAuth2Member(String id, String name, String providerType, String accessToken, String refreshToken, LocalDateTime expiresIn) {
        super(id, name);
        this.providerType = providerType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public void updateInfo(String name, String accessToken, LocalDateTime expiresIn) {
        this.name = name;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }
}
