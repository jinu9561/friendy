package web.mvc.entity.user;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetupRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "meetup_record_seq")
    @SequenceGenerator(name ="meetup_record_seq" , allocationSize = 1 , sequenceName = "meetup_record_seq")
    private Long meetUpRecordSeq;
    @CreationTimestamp
    private LocalDateTime userRegDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserDetail userDetail;
}
