package web.mvc.service.chatting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.mvc.dto.chat.MessageDTO;
import web.mvc.entity.chatting.MessageLog;
import web.mvc.repository.chatting.MessageLogRepository;

import java.util.List;


@RequiredArgsConstructor
@Service
public class MessageLogServiceImpl implements MessageLogService{

    private final MessageLogRepository messageLogRepository;

    @Override
    public List<MessageLog> messageList() {
        return null;
    }

    @Override
    public void insertMessage(MessageDTO messageDTO) {
        MessageLog messageLog = MessageLog.builder().
        chattingContent(messageDTO.getChattingContent())
                .roomId(messageDTO.getChatRoomId())
                .userSeq(messageDTO.getUserSeq())
                        .build();

        messageLogRepository.save(messageLog);
    }
}
