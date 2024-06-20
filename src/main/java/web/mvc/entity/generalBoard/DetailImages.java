package web.mvc.entity.generalBoard;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailImages {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "photoBoardDetailImgSeq")
    @SequenceGenerator(name ="photoBoardDetailImgSeq" , allocationSize = 1 , sequenceName = "photoBoardDetailImgSeq")
    private Long photoBoardDetailImgSeq;
    @Column
    private String photoBoardDetailImgName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photoBoardSeq")  // 외래 키를 지정
    private PhotoBoard photoBoard;


}
