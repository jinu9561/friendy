package web.mvc.dto.meetUpBoard;


import lombok.*;
import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MeetUpBoardDTO {
    private Long meetUpSeq;
    private Long userSeq;
    private String meetUpName;
    private String meetUpDesc;
    private Long meetUpMainImg;
    private String meetUpPwd;
    private int meetUpMaxEntry;
    private  Date  meetUpDeadLine;
    private int  meetUpStatus;
}
