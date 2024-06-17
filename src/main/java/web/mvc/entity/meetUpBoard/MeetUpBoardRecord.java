//package web.mvc.entity.meetUpBoard;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//
//import java.util.Date;
//
//@Entity
//@Builder
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//
//public class MeetUpBoardRecord {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meetup_record_seq")
//    @SequenceGenerator(allocationSize = 1, sequenceName = "meetup_record_seq", name = "meetup_record_seq")
//    private Long meetUpRecordSeq;
//
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "meetup_seq")
//    private MeetUpBoard meetUpBoard2;
//
//    @CreationTimestamp
//    private Date meetUpRegDate;
//
//}
