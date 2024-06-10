package web.mvc.entity.meetUpBoard;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class MeetUpBoardRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meetup_detail_img_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "meetup_detail_img_seq", name = "meetup_detail_img_seq")
    private Long meetUpRecordSeq;

    @JoinColumn(name = "meetup_seq")
    private Long meetUpSeq;

    @CreationTimestamp
    private Date meetUpRegDate;

}
