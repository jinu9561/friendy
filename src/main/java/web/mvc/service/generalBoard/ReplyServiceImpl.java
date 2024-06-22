package web.mvc.service.generalBoard;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.dto.generalBoard.ReplyDTO;
import web.mvc.entity.generalBoard.CommunityBoard;
import web.mvc.entity.generalBoard.Reply;
import web.mvc.repository.generalBoard.CommunityBoardRepository;
import web.mvc.repository.generalBoard.ReplyRepository;
import web.mvc.repository.user.UserRepository;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
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
        log.info("Fetching all replies by commBoardSeq: {}", commBoardSeq);
        CommunityBoard communityBoard = communityBoardRepository.findById(commBoardSeq).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글이 없습니다. seq = " + commBoardSeq));
        List<Reply> fetchedReplies = communityBoard.getReplyList();
        log.info("Fetched {} replies", fetchedReplies.size());
        return fetchedReplies;
    }

    // 새로운 댓글을 생성하는 메서드
    @Transactional
    @Override
    public ReplyDTO addReply(ReplyDTO replyDTO) {
        
        //해당 게시물이 있는지 조회
        CommunityBoard communityBoard = communityBoardRepository.findById(replyDTO.getCommBoardSeq()).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. seq = " + replyDTO.getCommBoardSeq()));

        //dto->entity로 변환
        Reply reply = replyDTO.toEntity();
        reply.setCommunityBoard(communityBoard);
        
        //reply 저장
        Reply savedReply = replyRepository.save(reply);
        log.info("Reply created with SEQ: {}", savedReply.getReplySeq());
        return savedReply.toDTO();
    }



    // 특정 댓글을 삭제하는 메서드
    @Transactional
    @Override
    public String deleteReply(Long replySeq) {
        log.info("Deleting reply with SEQ: {}", replySeq);
        Reply reply = replyRepository.findById(replySeq).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없습니다. seq = " + replySeq));
        replyRepository.delete(reply);
        log.info("Reply deleted with SEQ: {}", replySeq);
        return "댓글이 삭제되었습니다.";
    }



}
