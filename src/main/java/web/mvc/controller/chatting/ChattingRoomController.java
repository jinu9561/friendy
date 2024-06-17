package web.mvc.controller.chatting;



import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.chat.ChattingRoomDTO;
import web.mvc.entity.chatting.ChattingRoom;
import web.mvc.entity.chatting.MessageLog;
import web.mvc.entity.user.Users;
import web.mvc.repository.user.UserRepository;
import web.mvc.service.chatting.ChattingRoomService;
import web.mvc.service.chatting.MessageLogService;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
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


    @GetMapping("/joinRoom/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId){

        System.out.println("룸아이디"+roomId);
        List<MessageLog> messageLogList= chattingRoomService.findMessageLog(roomId);

        System.out.println("출력된 메세지 로그"+messageLogList.toString());
        
        return ResponseEntity.status(HttpStatus.OK).body(messageLogList);
    }

}
