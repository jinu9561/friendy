package web.mvc.dto.generalBoard;

import lombok.*;

import org.springframework.stereotype.Component;
import web.mvc.entity.generalBoard.Reply;

import web.mvc.repository.generalBoard.CommunityBoardRepository;
import web.mvc.repository.user.UserRepository;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class ReplyDTO {

    private UserRepository userRepository;
    private CommunityBoardRepository communityBoardRepository;

    private Long replySeq;
    private Long userSeq;
    private Long commBoardSeq;
    private String replyContent;
    private LocalDateTime replyRegDate;
    private String nickName;



    public Reply toEntity(){
        return Reply.builder()
                .user(userRepository.findById(this.userSeq).orElseThrow(()->new IllegalArgumentException("해당 사용자가 없습니다.")))
                .communityBoard(communityBoardRepository.findById(this.commBoardSeq).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다.")))
                .replyContent(this.replyContent)
                .build();
    }
}
