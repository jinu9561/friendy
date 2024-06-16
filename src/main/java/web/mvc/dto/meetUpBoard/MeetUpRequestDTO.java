package web.mvc.dto.meetUpBoard;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MeetUpRequestDTO {
    private Long meetUpRequestSeq;
    private Long meetUpSeq;
    private Long userSeq;
    private int meetUpRequestStatus;
}
