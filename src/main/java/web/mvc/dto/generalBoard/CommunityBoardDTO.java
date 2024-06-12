package web.mvc.dto.generalBoard;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@AllArgsConstructor //모든 멤버를 인자(parameter)로 받아 DTO객체를 생성
@NoArgsConstructor
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

