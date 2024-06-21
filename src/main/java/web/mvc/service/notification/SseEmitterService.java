package web.mvc.service.notification;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.taglibs.standard.extra.spath.Step;
import org.springframework.stereotype.Service;
import web.mvc.entity.notification.Notification;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class SseEmitterService {

    private final List<HttpServletResponse> clients = new CopyOnWriteArrayList<>();

    public void addClient(HttpServletResponse response) {
        clients.add(response);
    }

    public void sendNotification(String message) {
        log.info("@@@Sending notification: {}", message);
        clients.forEach(response -> {
            try {
                response.getWriter().write("data: " + message + "\n\n");
                response.getWriter().flush();
            } catch (IOException e) {
                log.error("@@@@Error broadcasting notification", e);
                clients.remove(response);  // 에러가 발생하면 클라이언트를 제거
            }
        });
    }
}