package com.nexters.rezoom.core.domain.notification.domain;

/**
 * Created by momentjin@gmail.com on 2020-06-07
 * Github : http://github.com/momentjin
 */
public class NotificationMessageFactory {

    public static NotificationMessage createDeadlineAlarmMessage(Notification notification) {
        String title = "자기소개서 마감일 알림";
        String contents = String.format("[%s] 자기소개서의 마감일이 %s일(%s시간) 남았습니다."
                , notification.getCompanyName()
                , notification.getRemainingDays()
                , notification.getRemainingHours());

        return new NotificationMessage(title, contents);
    }
}
