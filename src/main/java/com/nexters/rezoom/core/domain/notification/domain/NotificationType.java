package com.nexters.rezoom.core.domain.notification.domain;

import com.nexters.rezoom.core.domain.member.domain.Account;
import com.nexters.rezoom.core.infra.notification.Notifier;
import com.nexters.rezoom.core.infra.notification.impl.EmailNotifier;
import com.nexters.rezoom.core.infra.notification.impl.KakaoNotifier;
import com.nexters.rezoom.core.infra.notification.impl.NoneNotifier;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by momentjin@gmail.com on 2019-09-05
 * Github : http://github.com/momentjin
 */
public enum NotificationType {

    EMAIL(0, new EmailNotifier()),
    KAKAO(1, new KakaoNotifier()),
    NONE(9, new NoneNotifier());

    private static final Map<Integer, NotificationType> lookup = new HashMap<>();

    static {
        for (NotificationType notificationType : NotificationType.values()) {
            lookup.put(notificationType.typeNo, notificationType);
        }
    }

    private int typeNo;
    private Notifier notifier;

    NotificationType(int typeNo, Notifier notifier) {
        this.typeNo = typeNo;
        this.notifier = notifier;
    }

    public static NotificationType getValue(int typeNo) {
        return lookup.getOrDefault(typeNo, NotificationType.NONE);
    }

    public void notifyToClient(Account account, NotificationMessage message) {
        notifier.notifyToClient(account, message);
    }
}
