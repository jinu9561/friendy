package web.mvc.entity.user;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "interest_seq")
    @SequenceGenerator(name ="interest_seq" , allocationSize = 1 , sequenceName = "interest_seq")
    private Long interestSeq;
    @Column(length = 10)
    private String interestCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_seq")  // 외래 키를 지정
    private Profile profile;

}
