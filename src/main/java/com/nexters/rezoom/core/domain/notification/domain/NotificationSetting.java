package com.nexters.rezoom.core.domain.notification.domain;

import com.nexters.rezoom.core.domain.member.domain.Account;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by momentjin@gmail.com on 2019-09-05
 * Github : http://github.com/momentjin
 */

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationSetting {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long accountPK;

    @Column(name = "notification_type")
    private NotificationType notificationType;

    public NotificationSetting(Account account, NotificationType notificationType) {
        this.accountPK = account.getPK();
        this.notificationType = notificationType;
    }

    public void notifyToClient(Account account, NotificationMessage message) {
        notificationType.notifyToClient(account, message);
    }

    public static NotificationSetting createDefaultSetting(Account account) {
        return new NotificationSetting(account, NotificationType.NONE);
    }
}
