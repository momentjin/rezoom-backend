package com.nexters.rezoom.core.domain.member.application;

import com.nexters.rezoom.core.domain.member.domain.Account;
import com.nexters.rezoom.core.global.exception.BusinessException;
import com.nexters.rezoom.core.global.exception.ErrorType;
import com.nexters.rezoom.core.domain.member.domain.repository.AccountRepository;
import com.nexters.rezoom.core.domain.member.api.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RezoomMemberService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDto.ViewRes getMemberInfo(String username) {

        Optional<Account> optionalMember = accountRepository.findByUsername(username);
        if (!optionalMember.isPresent())
            throw new BusinessException(ErrorType.MEMBER_NOT_FOUND);

        return new MemberDto.ViewRes(optionalMember.get());
    }

    public void signUp(MemberDto.SignUpReq req) {

        accountRepository.findByUsername(req.getUsername()).ifPresent(member -> {
            throw new BusinessException(ErrorType.USER_NAME_DUPLICATION);
        });

        Account member = new Account(req.getUsername(), req.getName(), passwordEncoder.encode(req.getPassword()));
        accountRepository.save(member);
    }

    @Transactional
    public void updateMemberInfo(Long memberPK, MemberDto.UpdateReq req) {
        Account findMember = accountRepository.findById(memberPK)
                .orElseThrow(() -> new BusinessException(ErrorType.MEMBER_NOT_FOUND));

        findMember.updateInfo(req.getName(), req.getMotto());
    }
}
