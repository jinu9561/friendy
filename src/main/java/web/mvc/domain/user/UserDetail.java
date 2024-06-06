package web.mvc.domain.user;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "User_detail_seq")
    @SequenceGenerator(name ="User_detail_seq" , allocationSize = 1 , sequenceName = "User_detail_seq")
    private Long UserDetailSeq;
    private int userState;
    @CreationTimestamp
    private LocalDateTime userRegDate;
    @UpdateTimestamp
    private LocalDateTime userUpdateDate;
    private int userRate;
    private int userJelly;
    @Column(length = 100)
    private String Role;
    private LocalDateTime lastLoginDate;

    @OneToOne(fetch = FetchType.LAZY)
    private Users user;
    @OneToMany(mappedBy = "userDetail" , cascade = CascadeType.ALL)
    private List<MeetupRecord> meetupRecord;


}
