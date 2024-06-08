package web.mvc.entity.partyBoard;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partyboard_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "partyboard_seq", name = "partyboard_seq")
    private Long partySeq;

    @JoinColumn(name = "member_id")
    private Long userSeq;


    @Column(length = 50)
    private String partyName;

    @Column(length = 100)
    private String partyDesc;

    @JoinColumn(name = "party_detail_img_seq)")
    private Long partyMainImg;

    @CreationTimestamp
    private Date partyRegDate;

    @UpdateTimestamp
    private Date partyUpdateTime;

    @Column(length = 100)
    private String partyBoardPwd;

    private int partyMaxEntry;

    private  Date partDeadLine;

    private int partyStatus;


}
