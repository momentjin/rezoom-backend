package com.nexters.rezoom.core.domain.notification.application;

import com.nexters.rezoom.core.domain.notification.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by momentjin@gmail.com on 2020-06-07
 * Github : http://github.com/momentjin
 */
@Service
@RequiredArgsConstructor
public class NotificationSendService {

    private final NotificationRepository notificationRepository;
    private final NotificationSettingRepository notificationSettingRepository;

    public void sendNotifications() {

        List<Notification> notifications = notificationRepository.findAllByIsChecked(false);
        for (Notification notification : notifications) {

            Long accountPK = notification.getAccountPK();

            List<NotificationSetting> notificationSettings = this.notificationSettingRepository.findAllByAccountPK(accountPK);
            NotificationMessage message = NotificationMessageFactory.createDeadlineAlarmMessage(notification);

            for (NotificationSetting setting : notificationSettings) {
                setting.notifyToClient(null, message); // todo : 설계 잘못함
            }
        }
    }
}
