package web.mvc.handler.chatting;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import web.mvc.dto.chat.MessageDTO;
import web.mvc.entity.user.Users;
import web.mvc.repository.user.UserRepository;
import web.mvc.service.chatting.MessageLogService;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    //    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final Map<String, Map<String, WebSocketSession>> chatRooms = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final MessageLogService messageLogService;
    @Override

    //웹소켓 에 ㅇ연결되면 호출되는 메서드.
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        String roomId = session.getHandshakeHeaders().getFirst("roomId");
        System.out.println("해당 세션에 접속됨 : " + session.getId());
        System.out.println("roomId : " + roomId);
        if (roomId == null || roomId.isEmpty()) {
            session.close();
            return;
        }

        Map<String, WebSocketSession> sessions = chatRooms.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>());
        sessions.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String roomId = session.getHandshakeHeaders().getFirst("roomId");
        String userSeq = session.getHandshakeHeaders().getFirst("userSeq");

        System.out.println("메세지 도착 roomId: " + roomId);

        if (roomId == null || roomId.isEmpty()) {
            return;
        }

        Map<String, WebSocketSession> sessions = chatRooms.get(roomId);

        if (sessions == null) {
            return;
        }


        // 채팅방 내 모든 사용자에게 메시지 전송
        for (WebSocketSession ws : sessions.values()) {
            try {
                Optional<Users> optionalUsers = userRepository.findById(Long.valueOf(userSeq));
                if (optionalUsers.isPresent()) {
                    Users users = optionalUsers.get();
                    Long seq = users.getUserSeq();
                    System.out.println("유저시퀀스는"+seq);
                    String nickname = users.getNickName();
                    String content= message.getPayload();
                    // 메시지 객체에 userSeq 정보 포함

                    JsonNode messageJson = objectMapper.createObjectNode()
                            .put("message", message.getPayload())
                            .put("senderNickname", nickname);
                    ws.sendMessage(new TextMessage(messageJson.toString()));
                    MessageDTO messageDTO= MessageDTO.builder()
                            .chatRoomId(roomId)
                            .userSeq(seq)
                            .chattingContent(content)
                            .build();
                    messageLogService.insertMessage(messageDTO);
                    System.out.println("정상 삽입됐나 ?");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        for (Map<String, WebSocketSession> sessions : chatRooms.values()) {

            sessions.remove(session.getId());
            System.out.println("세션 종료");

        }
    }
}