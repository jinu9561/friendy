package web.mvc.entity.generalBoard;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import web.mvc.entity.user.Users;

import java.time.LocalDateTime;


@ToString //나중에 뺄수도(-> 무한루프 발생 가능성?)
@Setter
@Getter
@Entity
@Table(name = "PhotoBoard")
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



    // Getters and Setters
}
