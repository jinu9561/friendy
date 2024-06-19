package web.mvc.service.chatting;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import web.mvc.dto.chat.MessageDTO;
import web.mvc.entity.chatting.ChattingRoom;
import web.mvc.entity.chatting.MessageLog;
import web.mvc.entity.user.Users;
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
        System.out.println("여기인가요");
        Users users = Users.builder().
                userSeq(messageDTO.getUserSeq()).
                build();


        ChattingRoom chattingRoom= ChattingRoom.builder().
                chattingroomSeq(messageDTO.getChattingRoomSeq()).
                build();

        System.out.println("MessageLogServiceImpl chattingRoom.getChattingroomSeq  =  "+chattingRoom.getChattingroomSeq());
        System.out.println("MessageLogServiceImpl users.getUserSeq  =  " + users.getUserSeq());

        MessageLog messageLog = MessageLog.builder().
        chattingContent(messageDTO.getChattingContent())
                .chattingroom(chattingRoom)
                .user(users)
                        .build();

        messageLogRepository.save(messageLog);
    }
}
