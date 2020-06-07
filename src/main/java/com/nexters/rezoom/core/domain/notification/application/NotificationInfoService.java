package com.nexters.rezoom.core.domain.notification.application;

import com.nexters.rezoom.core.domain.coverletter.domain.Coverletter;
import com.nexters.rezoom.core.domain.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.core.domain.coverletter.domain.Deadline;
import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.core.domain.member.domain.MemberRepository;
import com.nexters.rezoom.core.domain.notification.api.dto.NotificationDto;
import com.nexters.rezoom.core.domain.notification.domain.Notification;
import com.nexters.rezoom.core.domain.notification.domain.NotificationRepository;
import com.nexters.rezoom.core.global.exception.BusinessException;
import com.nexters.rezoom.core.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by momentjin@gmail.com on 2019-09-02
 * Github : http://github.com/momentjin
 */

@Service
@RequiredArgsConstructor
public class NotificationInfoService {

    private final MemberRepository memberRepository;
    private final CoverletterRepository coverletterRepository;
    private final NotificationRepository notificationRepository;

    public NotificationDto.ListRes getNotifications(Member member) {
        List<Notification> notifications = notificationRepository.findAllByMemberOrderByCreatedAtDesc(member);

        return new NotificationDto.ListRes(notifications);
    }

    @Transactional
    public Notification toggleCheck(Member member, Long notificationId) {
        Notification notification = notificationRepository.findByIdAndMember(notificationId, member)
                .orElseThrow(() -> new BusinessException(ErrorType.NOTIFICATION_NOT_FOUND));

        notification.toggleChecked();
        return notification;
    }

    public void createNotifications() {

        List<Coverletter> coverletters = coverletterRepository
                .findAllByDeadlineGreaterThanEqual(Deadline.now());

        for (Coverletter coverletter : coverletters) {
            Notification notification = Notification.builder()
                    .member(coverletter.getMember())
                    .companyName(coverletter.getCompanyName())
                    .coverletterId(coverletter.getId())
                    .remainingDays(coverletter.getDeadline().getRemainingDays())
                    .remainingHours(coverletter.getDeadline().getRemainingHours())
                    .build();

            notificationRepository.save(notification);
        }
    }
}
