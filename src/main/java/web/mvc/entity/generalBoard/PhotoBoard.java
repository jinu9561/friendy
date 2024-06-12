package web.mvc.entity.generalBoard;

import jakarta.persistence.*;
import lombok.*;
import web.mvc.entity.user.Users;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity//서버 실행시에 해당 객체로 테이블 매핑생성
@Builder
public class PhotoBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PHOTO_BOARD_SEQ")
    private Long photoBoardSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ", nullable = false)
    private Users user;

    @Column(name = "PHOTO_BOARD_TITLE", nullable = false)
    private String photoBoardTitle;

    @Column(name = "PHOTO_IMG_SRC", nullable = false)
    private String photoImgSrc;

    @Column(name = "INTEREST_SEQ", nullable = false)
    private Long interestSeq;

    @Column(name = "PHORTO_BOARD_REGDATE", nullable = false)
    private LocalDateTime photoBoardRegDate;

    @Column(name = "PHOTO_UPDATE_DATE", nullable = false)
    private LocalDateTime photoUpdateDate;

    @Column(name = "PHOTO_BOARD_PWD", nullable = false)
    private String photoBoardPwd;

    @Column(name = "PHOTO_BOARD_LIKE")
    private int photoBoardLike;

    // 엔티티가 저장되기 전에 호출되는 메서드
    @PrePersist
    protected void onCreate() {
        this.photoBoardRegDate = LocalDateTime.now();
        this.photoUpdateDate = LocalDateTime.now();
    }

    // 엔티티가 업데이트되기 전에 호출되는 메서드
    @PreUpdate
    protected void onUpdate() {
        this.photoUpdateDate = LocalDateTime.now();
    }
}
