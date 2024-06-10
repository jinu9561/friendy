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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partyboard_detail_img_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "partyboard_detail_img_seq", name = "partyboard_detail_img_seq")
    private Long partyRecordSeq;

    @JoinColumn(name = "partyboard_seq")
    private Long partySeq;

    @CreationTimestamp
    private Date partyRegDate;

}
