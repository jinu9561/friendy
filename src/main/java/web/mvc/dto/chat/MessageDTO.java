package web.mvc.dto.chat;


import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MessageDTO {

    Long chattingRoomSeq;
    String chatRoomId;
    Long userSeq;
    String sender;
    String chattingContent;
    Date chattingCreateDate;

}
