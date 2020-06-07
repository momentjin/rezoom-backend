package com.nexters.rezoom.core.domain.notification.application;

import com.nexters.rezoom.core.domain.member.domain.Account;
import com.nexters.rezoom.core.domain.member.domain.repository.AccountRepository;
import com.nexters.rezoom.core.domain.notification.domain.*;
import com.nexters.rezoom.core.global.exception.BusinessException;
import com.nexters.rezoom.core.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by momentjin@gmail.com on 2020-06-07
 * Github : http://github.com/momentjin
 */
@Service
@RequiredArgsConstructor
public class DeadlineNotificationSendService {

    private final AccountRepository accountRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationSettingRepository notificationSettingRepository;

    public void sendNotifications() {

        List<Notification> notifications = notificationRepository.findAllByIsChecked(false);
        for (Notification notification : notifications) {

            Long accountPK = notification.getAccountPK();
            Account account = this.accountRepository.findById(accountPK)
                    .orElseThrow(() -> new BusinessException(ErrorType.MEMBER_NOT_FOUND));

            List<NotificationSetting> notificationSettings = this.notificationSettingRepository.findAllByAccountPK(accountPK);
            NotificationMessage message = NotificationMessageFactory.createDeadlineAlarmMessage(notification);

            for (NotificationSetting setting : notificationSettings) {
                setting.notifyToClient(account, message);
            }
        }
    }
}
