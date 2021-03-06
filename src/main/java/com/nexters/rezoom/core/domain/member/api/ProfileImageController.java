package com.nexters.rezoom.core.domain.member.api;

import com.nexters.rezoom.core.domain.member.application.ProfileImageService;
import com.nexters.rezoom.core.domain.member.domain.Account;
import com.nexters.rezoom.core.global.dto.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

/**
 * Created by momentjin@gmail.com on 2019-10-23
 * Github : http://github.com/momentjin
 */

@RestController
@RequestMapping("/profile")
public class ProfileImageController {

    private final ProfileImageService profileImageService;

    public ProfileImageController(ProfileImageService profileImageService) {
        this.profileImageService = profileImageService;
    }

    @PutMapping
    public ApiResponse updateProfileImage(@AuthenticationPrincipal Account account, @RequestPart MultipartFile file) {
        profileImageService.createProfileImage(account, file);
        return ApiResponse.success();
    }

    @GetMapping
    public ApiResponse<String> getProfileImage(@AuthenticationPrincipal Account account) throws IOException {
        File profileImage = profileImageService.getProfileImage(account);
        byte[] profileImageBytes = Files.readAllBytes(profileImage.toPath());

        return ApiResponse.success(Base64.getEncoder().encodeToString(profileImageBytes));
    }
}
