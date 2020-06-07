package com.nexters.rezoom.core.domain.member.api;

import com.nexters.rezoom.core.domain.member.api.dto.MemberDto;
import com.nexters.rezoom.core.domain.member.application.RezoomMemberService;
import com.nexters.rezoom.core.domain.member.domain.Account;
import com.nexters.rezoom.core.global.dto.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final RezoomMemberService service;

    public MemberController(RezoomMemberService service) {
        this.service = service;
    }

    // login api는 spring security에 의해 자동생성됌 (path: /login)

    @GetMapping(value = "/me")
    public ApiResponse<MemberDto.ViewRes> getCurrentUserInfo(@AuthenticationPrincipal Account account) {
        return ApiResponse.success(service.getMemberInfo((account.getUsername())));
    }

    @PostMapping(value = "/signup")
    public ApiResponse signUp(@Valid @RequestBody MemberDto.SignUpReq req) {
        service.signUp(req);
        return ApiResponse.success();
    }

    @PutMapping
    public ApiResponse updateMemberInfo(@AuthenticationPrincipal Account account, @Valid @RequestBody MemberDto.UpdateReq req) {
        service.updateMemberInfo(account.getPK(), req);
        return ApiResponse.success();
    }
}
