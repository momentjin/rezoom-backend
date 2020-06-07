package com.nexters.rezoom.core.infra.notification;

import com.nexters.rezoom.core.domain.member.domain.Account;
import com.nexters.rezoom.core.domain.notification.domain.NotificationMessage;

/**
 * Created by momentjin@gmail.com on 2019-09-06
 * Github : http://github.com/momentjin
 */
public interface Notifier {
    void notifyToClient(Account account, NotificationMessage message);
}
