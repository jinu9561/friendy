package web.mvc.entity.chatting;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChattingRoomDetailImg {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chattingroomImg_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "chattingroomImg_seq", name = "chattingroomImg_seq")
    private int chattingroomImg;

    @Column(length = 200)
    private String chattingRoomImgSrc;

    @Column(length = 30)
    private String chattingRoomDetailImgType;


    @Column(length = 30)
    private String chattingRoomDetailImgSize;


}
