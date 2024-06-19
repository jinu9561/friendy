package web.mvc.entity.generalBoard;

import jakarta.persistence.*;
import lombok.*;
import web.mvc.entity.generalBoard.PhotoBoard;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoBoardDetailImg {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "photoBoardDetailImgSeq")
    @SequenceGenerator(name ="photoBoardDetailImgSeq" , allocationSize = 1 , sequenceName = "photoBoardDetailImgSeq")
    private Long photoBoardDetailImgSeq;
    @Column(length = 100)
    private String photoBoardDetailImgName;
    @Column(length = 300)
    private String photoBoardDetailImgSrc;
    @Column(length = 10)
    private String photoBoardDetailImgType;
    @Column(length = 100)
    private String photoBoardDetailImgSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photoBoardSeq")  // 외래 키를 지정
    private PhotoBoard photoBoard;

    public PhotoBoardDetailImg(PhotoBoard photoBoard) {
        this.photoBoard = photoBoard;
    }

}
