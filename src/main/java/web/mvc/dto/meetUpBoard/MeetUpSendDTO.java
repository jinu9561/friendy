package web.mvc.dto.meetUpBoard;

import lombok.*;
import web.mvc.entity.meetUpBoard.MeetUpBoardDetailImg;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MeetUpSendDTO {
    private Long meetUpSeq;
    private Long userSeq;
    private String interest;
    private String meetUpName;
    private String meetUpDesc;
    private String meetUpPeopleList;
    private List<String> meetUpBoardDetailImgNameList;
    private int meetUpPwd;
    private int meetUpMaxEntry;
    private  String  meetUpDeadLine;
    private int  meetUpStatus;
}
