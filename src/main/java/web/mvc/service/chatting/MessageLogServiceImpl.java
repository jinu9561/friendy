package web.mvc.service.chatting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.mvc.entity.chatting.MessageLog;

import java.util.List;


@RequiredArgsConstructor
@Service
public class MessageLogServiceImpl implements MessageLogService{
    @Override
    public List<MessageLog> messageList() {
        return null;
    }
}
