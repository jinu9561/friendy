package web.mvc.entity.chatting;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MessageLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messageLog_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "messageLog_seq", name = "messageLog_seq")
    private Long MessageSeq;

    @JoinColumn(name = "member_seq")
    private Long userSeq;

    @JoinColumn(name = "chattingroom_seq")
    private String roomId;

    private String chattingContent;

    @CreationTimestamp
    private Date chattingCreateDate;


}
