package web.mvc.dto.generalBoard;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PhotoBoardDTO {

    private Long photoBoardSeq;
    private Long userSeq;
    private String photoBoardTitle;
    private String photoImgSrc;
    private Long interestSeq;
    private LocalDateTime photoBoardRegDate;
    private LocalDateTime photoUpdateDate;
    private String photoBoardPwd;
    private int photoBoardLike;
}
