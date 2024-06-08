package web.mvc.domain.user;


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
public class JellyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "jelly_record_seq")
    @SequenceGenerator(name ="jelly_record_seq" , allocationSize = 1 , sequenceName = "jelly_record_seq")
    private Long jellyRecordSeq;
    private int jellyRecCategory;
    @Column(length = 300)
    private String jellyRecord;
    @CreationTimestamp
    private LocalDateTime jellyRegDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;


}
