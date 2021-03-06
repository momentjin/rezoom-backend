package com.nexters.rezoom.core.domain.coverletter.api;

import com.nexters.rezoom.core.domain.coverletter.api.dto.PageRequest;
import com.nexters.rezoom.core.domain.coverletter.api.dto.QuestionDto;
import com.nexters.rezoom.core.domain.coverletter.application.QuestionService;
import com.nexters.rezoom.core.domain.member.domain.Account;
import com.nexters.rezoom.core.global.dto.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService service;

    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @GetMapping("")
    public ApiResponse<Page<QuestionDto.ViewRes>> getList(@AuthenticationPrincipal Account account, PageRequest pageRequest) {
        pageRequest.setSort(Sort.by("id").descending());
        return ApiResponse.success(service.getList(account, pageRequest.of()));
    }

    @GetMapping("/{id}")
    public ApiResponse<QuestionDto.ViewRes> getView(@AuthenticationPrincipal Account account, @PathVariable Long id) {
        return ApiResponse.success(service.getView(id, account));
    }

    @GetMapping("/search")
    public ApiResponse<List<QuestionDto.ViewRes>> getQuestionsByHashtags(
            @AuthenticationPrincipal Account account, @RequestParam List<String> hashtags) {

        return ApiResponse.success(service.getQuestionsByHashtags(account, hashtags));
    }
}
