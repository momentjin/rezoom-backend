package com.nexters.rezoom.core.domain.notification.domain;

import com.nexters.rezoom.core.domain.member.domain.Account;
import com.nexters.rezoom.core.global.exception.BusinessException;
import com.nexters.rezoom.core.global.exception.ErrorType;
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

    public void notifyToClient(Account account, NotificationMessage message) {
        if (!this.accountPK.equals(account.getPK())) {
            throw new BusinessException(ErrorType.NO_PERMISSION);
        }
        notificationType.notifyToClient(account, message);
    }
}
