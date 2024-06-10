package web.mvc.controller.chatting;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.mvc.service.chatting.ChattingRoomService;
import web.mvc.service.chatting.MessageLogService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting")
public class ChattingRoomController {
    
    private final ChattingRoomService chattingRoomService;
    private final MessageLogService messageLogService;

    @GetMapping
    public void test(){
        System.out.println("이게맞냐");
    }

}
