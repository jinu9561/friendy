package web.mvc.service.chatting;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.mvc.entity.chatting.ChattingRoom;
import web.mvc.repository.chatting.ChattingRoomDetailImgRepository;
import web.mvc.repository.chatting.ChattingRoomRepository;
import web.mvc.repository.chatting.MessageLogRepository;

@RequiredArgsConstructor
@Service
public class ChattingRoomSerivceImpl implements ChattingRoomService {

   private final ChattingRoomRepository chattingRoomRepository;
   private final ChattingRoomDetailImgRepository chattingRoomDetailImgRepository;
   private final MessageLogRepository messageLogRepository;


    @Override
    public ChattingRoom createChattingRoom(ChattingRoom chattingRoom) {
        return null;
    }

    @Override
    public String deportChat(Long userId) {
        return null;
    }

    @Override
    public String deleteChatRoom(ChattingRoom chattingRoom) {
        return null;
    }
}
