package web.mvc.repository.chatting;


import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.entity.chatting.MessageLog;

public interface MessageLogRepository extends JpaRepository<MessageLog, Long> {
}
