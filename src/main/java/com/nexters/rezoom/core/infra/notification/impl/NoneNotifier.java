package com.nexters.rezoom.core.infra.notification.impl;

import com.nexters.rezoom.core.domain.member.domain.Account;
import com.nexters.rezoom.core.domain.notification.domain.NotificationMessage;
import com.nexters.rezoom.core.infra.notification.Notifier;

/**
 * Created by momentjin@gmail.com on 2019-09-06
 * Github : http://github.com/momentjin
 */
public class NoneNotifier implements Notifier {

    @Override
    public void notifyToClient(Account account, NotificationMessage message) {
        // do nothing.
    }
}
