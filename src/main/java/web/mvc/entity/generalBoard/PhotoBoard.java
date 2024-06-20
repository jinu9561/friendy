package web.mvc.entity.generalBoard;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import web.mvc.entity.user.Users;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity//서버 실행시에 해당 객체로 테이블 매핑생성
@Builder
public class PhotoBoard {

    @Id
    //시퀀스 전략을 사용하여 기본 키 값을 자동으로 생성하도록 설정. generator 속성의 값은 아래의 name과 일치해야 함.
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photoBoardSeq")
    //sequencName은 데이터베이스 시퀀스의 이름. name은 JPA에서 이 시퀀스를 식별하는 이름.
    @SequenceGenerator(allocationSize = 1, sequenceName = "photoBoardSeq", name = "photoBoardSeq")
    @Column(name = "PHOTO_BOARD_SEQ")
    private Long photoBoardSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ", nullable = false)
    private Users user; //PhotoBoard 테이블에 외래 키(Foreign Key) 컬럼인 USER_SEQ를 생성하여 Users 테이블의 기본 키와 연결.
    //테이블에 Users객체가 직접 저장되는 거 아님.

    @Column(name = "PHOTO_BOARD_TITLE", nullable = false)
    private String photoBoardTitle;

    @Column(name = "PHOTO_BOARD_MAIN_IMG_NAME", nullable = true)
    private String photoBoardMainImgName;

    @Column(name = "INTEREST_SEQ", nullable = false)
    private Long interestSeq;

    @Column(name = "PHOTO_BOARD_PWD", nullable = false)
    private String photoBoardPwd;

    @Column(name = "PHOTO_BOARD_LIKE")
    private int photoBoardLike;

    @CreationTimestamp
    @Column(name = "PHOTO_BOARD_REG_DATE", nullable = false)
    private LocalDateTime photoBoardRegDate;

    @UpdateTimestamp
    @Column(name = "PHOTO_BOARD_UPDATE_DATE", nullable = false)
    private LocalDateTime photoBoardUpdateDate;

    @Column(name = "PHOTO_BOARD_COUNT")
    private int photoBoardCount;

    @OneToMany(mappedBy = "photoBoard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetailImages> detailImages;
}


