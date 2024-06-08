package web.mvc.entity.partyBoard;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyDetailImg {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partyboard_detail_img_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "partyboard_detail_img_seq", name = "partyboard_detail_img_seq")
    private Long partyDetailImgSeq;


    @JoinColumn(name="partyboard_seq")
    private Long partySeq;

    @Column(length = 200)
    private String partyDetailImgSrc;

    @Column(length = 30)
    private String partyDetailImgType;


    @Column(length = 30)
    private String partyDetailImgSize;

}
