package com.nexters.rezoom.core.global.config.security.oauth2;

import com.nexters.rezoom.core.domain.member.application.OAuth2MemberService;
import com.nexters.rezoom.core.domain.member.domain.Account;
import com.nexters.rezoom.core.domain.member.domain.OAuth2Info;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created by momentjin@gmail.com on 2019-12-11
 * Github : http://github.com/momentjin
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Component
public class MyOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    private OAuth2MemberService oAuth2MemberService;

    @Autowired
    public MyOAuth2AuthorizedClientService(OAuth2MemberService oAuth2MemberService) {
        this.oAuth2MemberService = oAuth2MemberService;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient, Authentication authentication) {
        String providerType = oAuth2AuthorizedClient.getClientRegistration().getRegistrationId();
        OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String username = (String) oauth2User.getAttributes().get("id");
        String name = oauth2User.getName();

        OAuth2Info oAuth2Info = new OAuth2Info(
                providerType,
                accessToken.getTokenValue(),
                LocalDateTime.ofInstant(accessToken.getExpiresAt(), ZoneOffset.UTC)
        );

        Account account = new Account(username, name, oAuth2Info);
        oAuth2MemberService.signUp(account);
    }

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String s, String s1) {
        throw new NotImplementedException();
    }

    @Override
    public void removeAuthorizedClient(String s, String s1) {
        throw new NotImplementedException();
    }
}
