package web.mvc.dto.generalBoard;

import lombok.*;
import web.mvc.entity.generalBoard.CommunityBoard;
import web.mvc.entity.generalBoard.Reply;
import web.mvc.entity.user.Users;


@Setter
@Getter
@ToString
@AllArgsConstructor //모든 멤버를 인자(parameter)로 받아 DTO객체를 생성
@NoArgsConstructor
public class ReplyReqDTO {
    private Long replySeq;
    private long userSeq;
    private long boardSeq;
    private String replyContent;
    private String replyRegDate;



    // 사용자가 보낸 DTO에서 엔티티로 변환하는 메서드
    public Reply toEntity(Users user, CommunityBoard board) {
        return Reply.builder()
                .replySeq(this.replySeq)
                .user(user)
                .communityBoard(board)
                .replyContent(this.replyContent)
                .build();
    }



}
