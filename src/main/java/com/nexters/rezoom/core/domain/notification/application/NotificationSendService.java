package com.nexters.rezoom.core.domain.notification.application;

import com.nexters.rezoom.core.domain.member.domain.Member;
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

    public void sendNotifications() {

        List<Notification> notifications = notificationRepository.findAllByIsChecked(false);
        for (Notification notification : notifications) {

            Member receiver = notification.getMember();
            List<NotificationSetting> notificationSettings = receiver.getNotificationSettings();
            NotificationMessage message = NotificationMessageFactory.createDeadlineAlarmMessage(notification);

            for (NotificationSetting setting : notificationSettings) {
                setting.notifyToClient(receiver, message);
            }
        }
    }
}
