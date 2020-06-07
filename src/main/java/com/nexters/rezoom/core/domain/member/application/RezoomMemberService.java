package com.nexters.rezoom.core.domain.member.application;

import com.nexters.rezoom.core.domain.member.domain.RezoomMember;
import com.nexters.rezoom.core.global.exception.BusinessException;
import com.nexters.rezoom.core.global.exception.ErrorType;
import com.nexters.rezoom.core.domain.member.domain.RezoomMemberRepository;
import com.nexters.rezoom.core.domain.member.api.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RezoomMemberService {

    private final RezoomMemberRepository rezoomMemberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDto.ViewRes getMemberInfo(String username) {

        Optional<RezoomMember> optionalMember = rezoomMemberRepository.findByUsername(username);
        if (!optionalMember.isPresent())
            throw new BusinessException(ErrorType.MEMBER_NOT_FOUND);

        return new MemberDto.ViewRes(optionalMember.get());
    }

    public void signUp(MemberDto.SignUpReq req) {

        rezoomMemberRepository.findByUsername(req.getUsername()).ifPresent(member -> {
            throw new BusinessException(ErrorType.USER_NAME_DUPLICATION);
        });

        RezoomMember member = new RezoomMember(req.getUsername(), req.getName(), passwordEncoder.encode(req.getPassword()));
        rezoomMemberRepository.save(member);
    }

    @Transactional
    public void updateMemberInfo(Long memberPK, MemberDto.UpdateReq req) {
        RezoomMember findMember = rezoomMemberRepository.findById(memberPK)
                .orElseThrow(() -> new BusinessException(ErrorType.MEMBER_NOT_FOUND));

        findMember.updateInfo(req.getName(), req.getMotto());
    }
}
