package com.nexters.rezoom.core.domain.notification.api;

import com.nexters.rezoom.core.domain.member.domain.Account;
import com.nexters.rezoom.core.domain.notification.api.dto.NotificationDto;
import com.nexters.rezoom.core.domain.notification.application.CoverletterNotificationInfoService;
import com.nexters.rezoom.core.global.dto.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Created by momentjin@gmail.com on 2019-09-03
 * Github : http://github.com/momentjin
 */
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final CoverletterNotificationInfoService coverletterNotificationInfoService;

    public NotificationController(CoverletterNotificationInfoService coverletterNotificationInfoService) {
        this.coverletterNotificationInfoService = coverletterNotificationInfoService;
    }

    @GetMapping("")
    public ApiResponse<NotificationDto.ListRes> getNotifications(@AuthenticationPrincipal Account account) {
        return ApiResponse.success(coverletterNotificationInfoService.getNotifications(account));
    }

    @PutMapping("/{id}/toggle")
    public ApiResponse toggle(@AuthenticationPrincipal Account account, @PathVariable(name = "id") Long notificationId) {
        return ApiResponse.success(coverletterNotificationInfoService.toggleCheck(account, notificationId));
    }
}
