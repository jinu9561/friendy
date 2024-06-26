package web.mvc.handler.chatting;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
        log.info("Attempting connection: session {}", session);
        Map<String, String> queryParams = parseQueryParams(session.getUri().getQuery());
        String roomId = queryParams.get("roomId");
        Long userSeq = Long.valueOf(queryParams.get("userSeq"));
        Long chattingRoomSeq = Long.valueOf(queryParams.get("chattingRoomSeq"));

        printCurrentUsers();
        log.info("Session connected: sessionId {}, roomId {}, roomSeq {}", session.getId(), roomId, chattingRoomSeq);

        if (roomId == null || roomId.isEmpty()) {
            session.close();
            return;
        }

        Map<Long, WebSocketSession> userChattingRooms = userSessions.get(userSeq);
        if (userChattingRooms != null && userChattingRooms.containsKey(chattingRoomSeq)) {
            WebSocketSession existingSession = userChattingRooms.get(chattingRoomSeq);
            if (existingSession != null) {
                session.close();
                return;
            }
        }

        chatRooms.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>()).put(session.getId(), session);
        userSessions.computeIfAbsent(userSeq, k -> new ConcurrentHashMap<>()).put(chattingRoomSeq, session);
    }

    private Map<String, String> parseQueryParams(String query) {
        return Arrays.stream(query.split("&"))
                .map(param -> param.split("="))
                .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));
    }

    private void printCurrentUsers() {
        log.info("Current connected users:");
        chatRooms.forEach((roomId, sessions) -> {
            log.info("Room ID: {}", roomId);
            sessions.forEach((sessionId, session) -> log.info("Session ID: {}", sessionId));
        });
    }

    @Override
    @Transactional
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("WebSocket session received a message: {}", message.getPayload());
        String messageJason = message.getPayload();
        JSONObject jsonObject = new JSONObject(messageJason);
        String userSeq = jsonObject.getString("userSeq");
        System.out.println("userSeq" + userSeq);
        String jsonMessage = jsonObject.getString("message");
        System.out.println("jsonMessage" + jsonMessage);
        Map<String, String> queryParams = parseQueryParams(session.getUri().getQuery());
        String roomId = queryParams.get("roomId");
        Long chattingRoomSeq = Long.valueOf(queryParams.get("chattingRoomSeq"));
        System.out.println("roomId" + roomId);
        if (roomId == null || roomId.isEmpty()) {
            log.warn("Room ID is null or empty.");
            return;
        }
//
        Map<String, WebSocketSession> sessions = chatRooms.get(roomId);
        if (sessions == null) {
            log.warn("No sessions found for roomId: {}", roomId);
            return;
        }

        sessions.values().forEach(ws -> {
            try {
                Optional<Users> optionalUsers = userRepository.findById(Long.valueOf(userSeq));
                if (optionalUsers.isPresent()) {
                    Users users = optionalUsers.get();
                    Long seq = users.getUserSeq();
                    String nickname = users.getNickName();
                    String content = message.getPayload();
                    System.out.println("content"+content);
                    LocalTime currentTime = LocalTime.now();

                    // 시간과 분을 문자열로 포맷팅
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                    String formattedTime = currentTime.format(formatter);

                    JsonNode messageJson = objectMapper.createObjectNode()
                            .put("message", jsonMessage)
                            .put("senderNickname", nickname)
                                    .put("nowTime", formattedTime);

                        ws.sendMessage(new TextMessage(messageJson.toString()));

                    MessageDTO messageDTO = MessageDTO.builder()
                            .chattingRoomSeq(chattingRoomSeq)
                            .chatRoomId(roomId)
                            .userSeq(seq)
                            .chattingContent(content)
                            .build();
                    messageLogService.insertMessage(messageDTO);
                    log.info("Message logged successfully for user: {}", nickname);
                } else {
                    log.warn("User not found for userSeq: {}", userSeq);
                }
            } catch (IOException e) {
                log.error("Error sending message", e);
            }
        });
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        chatRooms.values().forEach(sessions -> sessions.remove(session.getId()));
        userSessions.values().forEach(userChattingRooms -> userChattingRooms.values().remove(session));
        log.info("Session closed: {}", session.getId());
    }
}
