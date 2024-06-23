package web.mvc.dto.generalBoard;

import lombok.*;

import org.springframework.stereotype.Component;
import web.mvc.entity.generalBoard.CommunityBoard;
import web.mvc.entity.generalBoard.Reply;

import web.mvc.entity.user.Users;
import web.mvc.repository.generalBoard.CommunityBoardRepository;
import web.mvc.repository.user.UserRepository;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDTO {

    private Long replySeq;
    private Long userSeq;
    private Long commBoardSeq;
    private String replyContent;
    private LocalDateTime replyRegDate;
    private String nickName;



    public Reply toEntity(Users user, CommunityBoard communityBoard){
        return Reply.builder()
                .replySeq(this.replySeq)
                .user(user)
                .communityBoard(communityBoard)
                .replyContent(this.replyContent)
                .build();
    }
}
