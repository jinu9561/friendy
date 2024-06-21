package web.mvc.dto.generalBoard;

import web.mvc.entity.generalBoard.Reply;

import java.time.LocalDateTime;

public class ReplyResDTO {
    private Long replySeq;
    private Long userSeq;
    private String nickname;
    private String replyContent;
    private LocalDateTime replyRegDate;


    /*엔티티에서 사용자에게 응답할 DTO로 변환하는 생성자*/
    public ReplyResDTO (Reply reply) {
        this.replySeq = reply.getReplySeq();
        this.userSeq = reply.getUser().getUserSeq();
        this.nickname = reply.getUser().getNickName();
        this.replyContent = reply.getReplyContent();
        this.replyRegDate = reply.getReplyRegDate();
    }
}

