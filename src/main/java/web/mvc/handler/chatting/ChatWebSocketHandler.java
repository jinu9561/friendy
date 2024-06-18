package web.mvc.handler.chatting;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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

    private final Map<String, Map<String, WebSocketSession>> chatRooms = new ConcurrentHashMap<>();
    private final Map<Long, Map<Long, WebSocketSession>> userSessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final MessageLogService messageLogService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        String roomId = session.getHandshakeHeaders().getFirst("roomId");
        Long userSeq = Long.valueOf(session.getHandshakeHeaders().getFirst("userSeq"));
        Long chattingRoomSeq = Long.valueOf(session.getHandshakeHeaders().getFirst("chattingRoomSeq"));

        printCurrentUsers();
        System.out.println("chattingRoomSeq: " + chattingRoomSeq);
        System.out.println("해당 세션에 접속됨 : " + session.getId());
        System.out.println("roomId : " + roomId);
        System.out.println("roomSeq : " + chattingRoomSeq);

        if (roomId == null || roomId.isEmpty()) {
            session.close();
            return;
        }

        // 동일한 userSeq와 chattingRoomSeq가 이미 존재하는지 확인
        Map<Long, WebSocketSession> userChattingRooms = userSessions.get(userSeq);
        System.out.println("userChattingRooms : "+userChattingRooms);
        if (userChattingRooms != null && userChattingRooms.containsKey(chattingRoomSeq)) {
            WebSocketSession existingSession = userChattingRooms.get(chattingRoomSeq);
            System.out.println("여기는??");
            if (existingSession != null) {
                // 기존 세션이 존재하면 새로운 세션을 닫고 기존 세션 재사용
                System.out.println("여기옴 ?");
                session.close();
                return;
            }
        }

        // 새로운 세션 추가
        Map<String, WebSocketSession> sessions = chatRooms.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>());
        sessions.put(session.getId(), session);

        userChattingRooms = userSessions.computeIfAbsent(userSeq, k -> new ConcurrentHashMap<>());
        userChattingRooms.put(chattingRoomSeq, session);
    }

    private void printCurrentUsers() {
        System.out.println("현재 접속 중인 사용자 목록:");
        for (String roomId : chatRooms.keySet()) {
            System.out.println("Room ID: " + roomId);
            Map<String, WebSocketSession> sessions = chatRooms.get(roomId);
            for (String sessionId : sessions.keySet()) {
                System.out.println("Session ID: " + sessionId);
            }
        }
    }

    @Override
    @Transactional
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String roomId = session.getHandshakeHeaders().getFirst("roomId");
        String userSeq = session.getHandshakeHeaders().getFirst("userSeq");
        Long chattingRoomSeq = Long.valueOf(session.getHandshakeHeaders().getFirst("chattingRoomSeq"));
        System.out.println("chattingRoomSeq: " + chattingRoomSeq);
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
                    System.out.println("유저시퀀스는 " + seq);
                    String nickname = users.getNickName();
                    String content = message.getPayload();

                    // 메시지 객체에 userSeq 정보 포함
                    JsonNode messageJson = objectMapper.createObjectNode()
                            .put("message", message.getPayload())
                            .put("senderNickname", nickname);
                    ws.sendMessage(new TextMessage(messageJson.toString()));
                    MessageDTO messageDTO = MessageDTO.builder()
                            .chattingRoomSeq(chattingRoomSeq)
                            .chatRoomId(roomId)
                            .userSeq(seq)
                            .chattingContent(content)
                            .build();
                    messageLogService.insertMessage(messageDTO);
                    System.out.println("정상 삽입됐나?");
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

        // userSessions에서도 세션 제거
        for (Map<Long, WebSocketSession> userChattingRooms : userSessions.values()) {
            userChattingRooms.values().remove(session);
        }
    }
}
