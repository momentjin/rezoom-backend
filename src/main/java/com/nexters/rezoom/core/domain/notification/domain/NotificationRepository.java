package com.nexters.rezoom.core.domain.notification.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by momentjin@gmail.com on 2019-09-02
 * Github : http://github.com/momentjin
 */

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByIdAndAccountPK(Long id, Long accountPK);
    List<Notification> findAllByAccountPKOrderByCreatedAtDesc(Long accountPK);
    List<Notification> findAllByIsChecked(boolean isChecked);
}
