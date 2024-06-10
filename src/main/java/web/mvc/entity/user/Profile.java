package web.mvc.entity.user;


import jakarta.persistence.*;
import lombok.*;
import web.mvc.enums.users.ImgStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "profile_seq")
    @SequenceGenerator(name ="profile_seq" , allocationSize = 1 , sequenceName = "profile_seq")
    private Long profileSeq;
    @Column(length = 300)
    private String profileMainImg;
    @Column(length = 10)
    private String profileMainImgType;
    @Column(length = 100)
    private String profileMainImgSize;
    @Column(length = 500)
    private String introduce;
    private ImgStatus imgStatus;

    @OneToOne(fetch = FetchType.LAZY)
    private Users user;
    @OneToMany(mappedBy = "profile" , cascade = CascadeType.ALL)
    private List<ProfileDetailImg> profileDetailImgList;
    @OneToMany(mappedBy = "profile" , cascade = CascadeType.ALL)
    private List<Interest> interestList;

    public Profile(Users user) {
        this.user = user;
        this.profileDetailImgList = new ArrayList<>();
        this.interestList = new ArrayList<>();
    }

}
