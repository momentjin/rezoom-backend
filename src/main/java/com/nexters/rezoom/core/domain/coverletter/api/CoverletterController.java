package com.nexters.rezoom.core.domain.coverletter.api;

import com.nexters.rezoom.core.domain.member.domain.Account;
import com.nexters.rezoom.core.global.dto.ApiResponse;
import com.nexters.rezoom.core.domain.coverletter.application.CoverletterService;
import com.nexters.rezoom.core.domain.coverletter.api.dto.CoverletterDto;
import com.nexters.rezoom.core.domain.coverletter.api.dto.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/coverletters")
public class CoverletterController {

    private final CoverletterService service;

    public CoverletterController(CoverletterService service) {
        this.service = service;
    }

    @PostMapping(value = "")
    public ApiResponse save(@AuthenticationPrincipal Account account, @Valid @RequestBody CoverletterDto.SaveReq req) {
        service.save(account, req);
        return ApiResponse.success();
    }

    @GetMapping(value = "")
    public ApiResponse<Page<CoverletterDto.ViewRes>> getList(@AuthenticationPrincipal Account account, final PageRequest pageRequest) {
        return ApiResponse.success(service.getList(account, pageRequest.of()));
    }

    @GetMapping(value = "{id}")
    public ApiResponse<CoverletterDto.ViewRes> getView(@AuthenticationPrincipal Account account, @PathVariable Long id) {
        return ApiResponse.success(service.getView(account, id));
    }

    @GetMapping(value = "/search")
    public ApiResponse<CoverletterDto.ListRes> getList(@AuthenticationPrincipal Account account, @RequestParam final String companyName) {
        return ApiResponse.success(service.searchByCompanyName(account, companyName));
    }

    @PutMapping(value = "/{id}")
    public ApiResponse update(@AuthenticationPrincipal Account account, @PathVariable Long id, @Valid @RequestBody CoverletterDto.UpdateReq req) {
        service.update(account, id, req);
        return ApiResponse.builder().build();
    }

    @DeleteMapping(value = "/{id}")
    public ApiResponse delete(@AuthenticationPrincipal Account account, @PathVariable Long id) {
        service.delete(account, id);
        return ApiResponse.success();
    }
}
