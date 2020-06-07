package com.nexters.rezoom.core.domain.member.application;

import com.nexters.rezoom.core.domain.member.domain.Account;
import com.nexters.rezoom.core.domain.member.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by momentjin@gmail.com on 2020-02-03
 * Github : http://github.com/momentjin
 */

@Service
@RequiredArgsConstructor
public class OAuth2MemberService {

    private final AccountRepository accountRepository;

    public void signUp(Account account) {

        Account findAccount = this.accountRepository.findById(account.getPK())
                .orElse(null);

        if (findAccount == null) {
            this.accountRepository.save(account);
        }

        // 이미 존재하는 경우, Update 처리
        else {
            findAccount.updateOAuth2Info(
                    findAccount.getName(),
                    findAccount.getOAuth2Info().getAccessToken(),
                    findAccount.getOAuth2Info().getExpiresIn());
        }
    }
}
