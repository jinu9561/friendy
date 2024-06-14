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
public class ReplyDTO {
    private Long replySeq;
    private long userSeq;
    private long boardSeq;
    private String replyContent;
    private String replyRegDate;
    private String replyUpdateDate;
    private int replyLike;
    private String replyPwd;

    // 사용자가 보낸 DTO에서 엔티티로 변환하는 메서드
    public Reply toReplyEntity(ReplyDTO replyDTO, Users user, CommunityBoard communityBoard) {
        return Reply.builder()
                .user(user)  // 조회된 사용자 객체를 설정
                .communityBoard(communityBoard)  // 조회된 커뮤니티 게시물 객체를 설정
                .replyContent(this.replyContent)
                .replyLike(this.replyLike)
                .replyPwd(this.replyPwd)
                .build();
    }

    // 엔티티에서 사용자에게 보낼 DTO로 변환하는 정적 메서드
    public static ReplyDTO fromReplyEntity(Reply reply) {
        return new ReplyDTO(
                reply.getReplySeq(),
                reply.getUser().getUserSeq(),
                reply.getCommunityBoard().getCommBoardSeq(),
                reply.getReplyContent(),
                reply.getReplyRegDate(),
                reply.getReplyUpdateDate(),
                reply.getReplyLike(),
                reply.getReplyPwd()
        );
    }
}
