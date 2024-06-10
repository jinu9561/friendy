package web.mvc.dto.partyBoard;


import lombok.*;
import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PartyBoardDTO {
    private Long partySeq;
    private Long userSeq;
    private String partyName;
    private String partyDesc;
    private Long partyMainImg;
    private String partyBoardPwd;
    private int partyMaxEntry;
    private  Date partDeadLine;
    private int partyStatus;
}
