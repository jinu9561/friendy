package web.mvc.service.notification;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import web.mvc.entity.notification.Notification;
import web.mvc.entity.user.Users;
import web.mvc.repository.notification.NotificationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> getNotificationsByUser(Users user) {
        return notificationRepository.findAllNotificationsByUser(user.getUserSeq());
    }

    @Override
    public void updateNotificationIsChecked(Long notificationSeq) {
        notificationRepository.updateNotificationIsChecked(1, notificationSeq);
    }

    @Override
    public boolean notificationIsChecked(Long notificationSeq) {
        Notification notification = notificationRepository.findById(notificationSeq).orElse(null);
        return notification != null && notification.getIsChecked() == 1;
    }

    @Override
    public void deleteNotification(Long notificationSeq) {
        notificationRepository.deleteNotification(notificationSeq);
    }

    @Override
    public String getNotificatedUrl(Notification notification) {
        return notification.getNotificationLinkUrl();
    }

    @Override
    public Notification getNotificationById(Long notificationSeq) {
        return notificationRepository.findById(notificationSeq).orElse(null);
    }
}
