package web.mvc.controller.chatting;



import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.chat.ChattingRoomDTO;
import web.mvc.dto.chat.MessageDTO;
import web.mvc.entity.chatting.ChattingRoom;
import web.mvc.entity.chatting.MessageLog;
import web.mvc.entity.user.Users;
import web.mvc.repository.user.UserRepository;
import web.mvc.service.chatting.ChattingRoomService;
import web.mvc.service.chatting.MessageLogService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.time.LocalDate;
import java.util.Random;


@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting")
public class ChattingRoomController {
    
    private final ChattingRoomService chattingRoomService;
    private final MessageLogService messageLogService;
    private final UserRepository userRepository;


    @PostMapping("/createRoom")
    public ResponseEntity<?> createRoom(@RequestBody ChattingRoomDTO chattingRoomDTO) {

        System.out.println(chattingRoomDTO.getUserId());
        chattingRoomService.createChattingRoom(chattingRoomDTO);

        return ResponseEntity.status(HttpStatus.OK).body("생성");
    }


    @GetMapping("/joinRoom/{chattingRoomSeq}")
    public ResponseEntity<?> joinRoom(@PathVariable Long chattingRoomSeq) {
        List<MessageLog> messageLogList = chattingRoomService.findMessageLog(chattingRoomSeq);


        MessageDTO messageDTO = new MessageDTO();
        List<MessageDTO> messageDTOList = new ArrayList<>();



        for (MessageLog messageLog : messageLogList) {

            Date date = messageLog.getChattingCreateDate();
            String timeOnly = new SimpleDateFormat("HH:mm").format(date);
            messageDTO = MessageDTO.builder()
                         .chattingRoomSeq(messageLog.getMessageSeq())
                         .chatRoomId(messageLog.getChattingroom().getRoomId())
                         .sender(messageLog.getUser().getNickName())
                    .userSeq(messageLog.getUser().getUserSeq())
                         .chattingContent(messageLog.getChattingContent())
                         .chattingCreateDate(timeOnly)
                         .build();
                 messageDTOList.add(messageDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(messageDTOList);
    }

}
