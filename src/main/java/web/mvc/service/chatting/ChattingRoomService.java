package web.mvc.service.chatting;


import web.mvc.entity.chatting.ChattingRoom;

import java.util.List;

public interface ChattingRoomService {

    ChattingRoom createChattingRoom(ChattingRoom chattingRoom) ;

//    List<Member> inviteMember(Long userId );

    String deportChat (Long userId);

    String deleteChatRoom(ChattingRoom chattingRoom);


}
