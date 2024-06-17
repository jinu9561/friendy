package web.mvc.entity.chatting;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChattingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chattingroom_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "chattingroom_seq", name = "chattingroom_seq")
    private Long chattingroomSeq;
    @JoinColumn(name = "chattingroomImg_seq")
    private Long chattingRoomImgSeq;
    @Column(length = 200)
    private String roomId;
    @CreationTimestamp
    private Date roomRegDate;


}