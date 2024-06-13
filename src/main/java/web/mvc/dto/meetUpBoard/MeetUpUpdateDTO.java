package web.mvc.dto.meetUpBoard;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MeetUpUpdateDTO {
    private Long meetUpSeq;
    private String meetUpName;
    private String meetUpDesc;
    private int meetUpPwd;
    private Long meetUpMainImg;
    private  String  meetUpDeadLine;
}