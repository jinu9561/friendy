package web.mvc.domain.user;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "user_seq")
    @SequenceGenerator(name ="user_seq" , allocationSize = 1 , sequenceName = "user_seq")
    private Long userSeq;
    @Column(length = 100)
    private String userId;
    @Column(length = 100)
    private String userPwd;
    @Column(length = 100)
    private String userName;
    @Column(length = 100)
    private String nickName;

    @Temporal(TemporalType.DATE)
    private Date birth;
    @Column(length = 300)
    private String address;
    @Column(length = 100)
    private String email;
    @Column(length = 100)
    private String phone;
    private int country;
    private int gender;

    @OneToOne(mappedBy = "user" , cascade = CascadeType.ALL)
    private UserDetail userDetail;
    @OneToOne(mappedBy = "user" , cascade = CascadeType.ALL)
    private Profile profile;
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<JellyRecord> jellyRecord;
}

