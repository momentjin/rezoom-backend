package com.nexters.rezoom.core.domain.member.application;

import com.nexters.rezoom.core.domain.member.domain.OAuth2Member;
import com.nexters.rezoom.core.domain.member.domain.repository.OAuth2MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by momentjin@gmail.com on 2020-02-03
 * Github : http://github.com/momentjin
 */

@Service
@RequiredArgsConstructor
public class OAuth2MemberService {

    private final OAuth2MemberRepository oAuth2MemberRepository;

    public void signUp(OAuth2Member member) {
        OAuth2Member findMember = oAuth2MemberRepository.findById(member.getPK())
                .orElse(null);

        if (findMember == null) {
            oAuth2MemberRepository.save(member);
        }

        // 이미 존재하는 경우, Update 처리
        else {
            findMember.updateInfo(
                    member.getName(),
                    member.getAccessToken(),
                    member.getExpiresIn());
        }
    }
}
