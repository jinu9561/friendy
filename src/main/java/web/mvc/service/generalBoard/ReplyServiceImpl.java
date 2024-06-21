package web.mvc.service.generalBoard;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.dto.generalBoard.ReplyReqDTO;
import web.mvc.dto.generalBoard.ReplyResDTO;
import web.mvc.entity.generalBoard.CommunityBoard;
import web.mvc.entity.generalBoard.Reply;
import web.mvc.repository.generalBoard.CommunityBoardRepository;
import web.mvc.repository.generalBoard.ReplyRepository;
import web.mvc.repository.user.UserRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService{

    @Autowired
    private final ReplyRepository replyRepository;
    @Autowired
    private final CommunityBoardRepository communityBoardRepository;
    @Autowired
    private final UserRepository usersRepository;

    //해당 번호 게시글의 모든 댓글을 가져오는 메서드
    @Transactional(readOnly = true)
    @Override
    public List<Reply> getRepliesByCommBoardSeq(Long commBoardSeq) {
        CommunityBoard communityBoard = communityBoardRepository.findById(commBoardSeq).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글이 없습니다. seq = " + commBoardSeq));
           List<Reply> replies = communityBoard.getReplyList();
           return replies;
        }

    // 새로운 댓글을 생성하는 메서드
    @Transactional
    @Override
    ReplyResDTO addReply(Long boardSeq, ReplyReqDTO replyReqDTO){

    }

    // 특정 댓글을 수정하는 메서드
    @Transactional
    @Override
    ReplyResDTO updateReply(Long replyId, ReplyReqDTO replyReqDTO){

    }

    // 특정 댓글을 삭제하는 메서드
    @Transactional
    @Override
    void deleteReply(Long replyId){

    }



}
