package com.nexters.rezoom.core.domain.notification.domain;

import com.nexters.rezoom.core.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by momentjin@gmail.com on 2020-06-07
 * Github : http://github.com/momentjin
 */
public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, Long> {

    Optional<NotificationSetting> findByMember(Member member);
}
