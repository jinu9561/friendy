package web.mvc.entity.meetUpBoard;


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
@ToString
public class MeetUpBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meetup_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "meetup_seq", name = "meetup_seq")
    private Long meetUpSeq;

    @JoinColumn(name = "user_seq")
    private Long userSeq;


    @Column(length = 50)
    private String meetUpName;

    @Column(length = 100)
    private String meetUpDesc;

    @JoinColumn(name = "party_detail_img_seq)")
    private Long meetUpMainImg;

    @CreationTimestamp
    private Date meetUpRegDate;

    @UpdateTimestamp
    private Date meetUpUpdateTime;

    private int meetUpPwd;

    private int meetUpMaxEntry;

    private  Date meetUpDeadLine;

    private int meetUpStatus;


}
