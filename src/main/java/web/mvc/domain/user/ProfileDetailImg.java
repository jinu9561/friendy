package web.mvc.domain.user;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDetailImg {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "profile_detailImg_seq")
    @SequenceGenerator(name ="profile_detailImg_seq" , allocationSize = 1 , sequenceName = "profile_detailImg_seq")
    private Long profileDetailImgSeq;
    @Column(length = 300)
    private String profileDetailImgSrc;
    @Column(length = 10)
    private String profileDetailImgType;
    @Column(length = 100)
    private String profileDetailImgSize;


    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile;


}
