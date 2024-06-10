package web.mvc.dto.generalBoard;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommunityBoardDTO {

    private Long commBoardSeq;
    private Long userSeq;
    private String boardTitle;
    private String boardContent;
    private int boardType;
    private int boardLike;
    private LocalDate boardRegDate;
    private LocalDate boardUpdateDate;
    private String boardPwd;
}
