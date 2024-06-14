package web.mvc.controller.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.entity.notification.Notification;
import web.mvc.entity.user.Users;
import web.mvc.service.notification.NotificationService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
@Slf4j
public class NotificationController {

    private final NotificationService notificationServicel;

    private final NotificationService notificationService;

    /**
     * 유저의 전체 알림 출력
     */
    @GetMapping("/list")
    public ResponseEntity<List<Notification>> getAllNotifications(Principal principal) {
        Users user = new Users();
        // Principal 객체에서 사용자 ID를 가져와서 Users 객체에 설정
        user.setUserSeq(Long.parseLong(principal.getName()));
        List<Notification> notifications = notificationService.getNotificationsByUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(notifications);
    }

    /**
     * 알림 읽음처리
     */
    @PostMapping("/read")
    public ResponseEntity<?> markNotificationAsRead(@RequestParam Long notificationSeq) {
        notificationService.updateNotificationIsChecked(notificationSeq);
        return ResponseEntity.status(HttpStatus.OK).body("알림이 읽음 처리되었습니다.");
    }

    /**
     * 읽지않은 알림이 있으면 boolean 값 true, 없으면 false
     */
    @GetMapping("/unread")
    public ResponseEntity<Boolean> hasUnreadNotifications(@RequestParam Long notificationSeq) {
        boolean hasUnread = notificationService.notificationIsChecked(notificationSeq);
        return ResponseEntity.status(HttpStatus.OK).body(hasUnread);
    }

    /**
     * 알림 삭제
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteNotification(@RequestParam Long notificationSeq) {
        notificationService.deleteNotification(notificationSeq);
        return ResponseEntity.status(HttpStatus.OK).body("알림이 삭제되었습니다.");
    }

    /**
     * 알림에 뜬 링크 클릭하면 url을 타고 해당 게시물로 이동
     */
    @GetMapping("/url")
    public ResponseEntity<String> getNotificationUrl(@RequestParam Long notificationSeq) {
        Notification notification = notificationService.getNotificationById(notificationSeq);
        if (notification == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("알림을 찾을 수 없습니다.");
        }
        String url = notificationService.getNotificatedUrl(notification);
        return ResponseEntity.status(HttpStatus.OK).body(url);
    }
}
