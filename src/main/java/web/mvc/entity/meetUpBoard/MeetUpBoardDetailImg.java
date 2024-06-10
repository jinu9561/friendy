package web.mvc.entity.meetUpBoard;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetUpBoardDetailImg {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partyboard_detail_img_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "partyboard_detail_img_seq", name = "partyboard_detail_img_seq")
    private Long partyDetailImgSeq;


    @JoinColumn(name="meetup_seq")
    private Long meetUpSeq;

    @Column(length = 200)
    private String meetUpDetailImgSrc;

    @Column(length = 30)
    private String meetUpDetailImgType;


    @Column(length = 30)
    private String meetUpDetailImgSize;

}
