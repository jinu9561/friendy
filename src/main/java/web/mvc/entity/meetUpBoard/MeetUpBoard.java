package web.mvc.entity.meetUpBoard;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import web.mvc.entity.user.MeetupRecord;
import web.mvc.entity.user.Users;

import java.util.Date;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq" )
    private Users user;

    @Column(length = 50)
    private String meetUpName;

    @Column(length = 100)
    private String meetUpDesc;

    @OneToMany(mappedBy = "meetUpBoard", cascade = CascadeType.ALL)
    private List<MeetUpBoardDetailImg> meetUpBoardDetailImgList;
    @CreationTimestamp
    private Date meetUpRegDate;

    @OneToMany(mappedBy = "meetUpBoard", cascade = CascadeType.ALL)
    private List<MeetUpRequest> meetUpRequestsList;

    @JoinColumn(name = "interest_seq")
    private String interest;

    @OneToOne(mappedBy = "meetUpBoard", cascade = CascadeType.ALL)
    private MeetupRecord meetupRecord;


    @UpdateTimestamp
    private Date meetUpUpdateTime;

    private String meetUpPeopleList;

    private int meetUpPwd;

    private int meetUpMaxEntry;

    private Date meetUpDeadLine;

    private int meetUpStatus;
}
