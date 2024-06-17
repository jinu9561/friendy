package web.mvc.entity.meetUpBoard;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MeetUpRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meetuprequest_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "meetuprequest_seq", name = "meetuprequest_seq")
    private Long meetUpRequestSeq;

    @JoinColumn(name="meetup_seq")
    private Long meetUpSeq;

    @Column(name ="user_seq")
    private Long userSeq;

    @ColumnDefault("0")
    private int meetUpRequestStatus;
    @CreationTimestamp
    private Date meetUpReqeustRegDate;
}
