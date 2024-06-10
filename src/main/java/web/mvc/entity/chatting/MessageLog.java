package web.mvc.entity.chatting;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meesageLog_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "meesageLog_seq", name = "meesageLog_seq")
    private Long MeseageSeq;

    @JoinColumn(name = "member_seq")
    private Long userSeq;

    @JoinColumn(name = "chattingroom_seq")
    private Long chattingRoomUrl;

    private String chattingContent;

    @CreationTimestamp
    private Date chattingCreateDate;


}
