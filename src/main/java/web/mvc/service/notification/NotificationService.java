package web.mvc.service.notification;

import web.mvc.entity.notification.Notification;
import web.mvc.entity.user.Users;

import java.util.List;

public interface NotificationService {

    /**
     * 유저의 전체 알림 출력
     * */
    List<Notification> getNotificationsByUser(Users user);

    /**
     * 알림 읽음처리
     */
    void updateNotificationIsChecked(Long notificationSeq);

    /**
     * 읽지않은 알림이 있으면 boolean 값 true, 없으면 false
     * */
    boolean notificationIsChecked(Long notificationSeq);

    /**
     * 알림 삭제
     * */
    void deleteNotification(Long notificationSeq);

    /**
     * 알림에 뜬 링크로 이동하는 url 반환
     */
    String getNotificatedUrl(Notification notification);

    public Notification getNotificationById(Long notificationSeq);

}
