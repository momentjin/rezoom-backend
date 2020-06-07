package com.nexters.rezoom.core.domain.coverletter.api;

import com.nexters.rezoom.core.domain.coverletter.application.ConverterService;
import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.core.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by momentjin@gmail.com on 2019-08-29
 * Github : http://github.com/momentjin
 */
@RestController
@RequestMapping("/converter")
@RequiredArgsConstructor
public class ConverterController {

    private ConverterService converterService;

    @PostMapping("")
    public ApiResponse convertFromFileToCoverletter(@AuthenticationPrincipal Member member, @RequestPart(name = "file") MultipartFile[] files) {
        converterService.convertFileToCoverletter(member, files);
        return ApiResponse.success();
    }
}
