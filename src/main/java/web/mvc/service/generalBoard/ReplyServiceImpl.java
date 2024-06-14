package web.mvc.service.generalBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.dto.generalBoard.ReplyDTO;
import web.mvc.entity.generalBoard.CommunityBoard;
import web.mvc.entity.generalBoard.Reply;
import web.mvc.entity.user.Users;
import web.mvc.repository.generalBoard.CommunityBoardRepository;
import web.mvc.repository.generalBoard.ReplyRepository;
import web.mvc.repository.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final CommunityBoardRepository communityBoardRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ReplyDTO> getRepliesByBoardSeq(Long boardSeq) {
        log.info("Fetching replies for community board with SEQ: {}", boardSeq);

        List<Reply> replies = replyRepository.findByCommunityBoardCommBoardSeq(boardSeq);

        return replies.stream()
                .map(ReplyDTO::fromReplyEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ReplyDTO getReplyById(Long replySeq) {
        log.info("Fetching reply with SEQ: {}", replySeq);

        Reply reply = replyRepository.findById(replySeq)
                .orElseThrow(() -> new RuntimeException("Reply not found with seq: " + replySeq));

        return ReplyDTO.fromReplyEntity(reply);
    }

    @Transactional
    @Override
    public ReplyDTO createReply(ReplyDTO replyDTO) {
        log.info("Creating reply for community board with SEQ: {}", replyDTO.getBoardSeq());

        Users user = userRepository.findById(replyDTO.getUserSeq())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + replyDTO.getUserSeq()));

        CommunityBoard communityBoard = communityBoardRepository.findById(replyDTO.getBoardSeq())
                .orElseThrow(() -> new RuntimeException("CommunityBoard not found with seq: " + replyDTO.getBoardSeq()));

        Reply reply = replyDTO.toReplyEntity(replyDTO, user, communityBoard);

        Reply savedReply = replyRepository.save(reply);
        log.info("Reply created with SEQ: {}", savedReply.getReplySeq());

        return ReplyDTO.fromReplyEntity(savedReply);
    }

    @Transactional
    @Override
    public ReplyDTO updateReply(ReplyDTO replyDTO) {
        long replySeq = replyDTO.getReplySeq();
        log.info("Updating reply with SEQ: {}", replySeq);

        Reply existingReply = replyRepository.findById(replySeq)
                .orElseThrow(() -> new RuntimeException("Reply not found with seq: " + replySeq));

        Users user = userRepository.findById(replyDTO.getUserSeq())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + replyDTO.getUserSeq()));

        CommunityBoard communityBoard = communityBoardRepository.findById(replyDTO.getBoardSeq())
                .orElseThrow(() -> new RuntimeException("CommunityBoard not found with seq: " + replyDTO.getBoardSeq()));

        existingReply.setReplyContent(replyDTO.getReplyContent());
        existingReply.setReplyLike(replyDTO.getReplyLike());
        existingReply.setReplyPwd(replyDTO.getReplyPwd());
        existingReply.setUser(user);
        existingReply.setCommunityBoard(communityBoard);

        Reply updatedReply = replyRepository.save(existingReply);
        log.info("Reply updated with SEQ: {}", updatedReply.getReplySeq());

        return ReplyDTO.fromReplyEntity(updatedReply);
    }

    @Transactional
    @Override
    public String deleteReply(Long replySeq) {
        log.info("Deleting reply with SEQ: {}", replySeq);

        //삭제하려는 댓글 엔티티 조회
        Reply reply = replyRepository.findById(replySeq)
                .orElseThrow(() -> new RuntimeException("Reply not found with seq: " + replySeq));

        // 댓글 삭제
        replyRepository.delete(reply);
        log.info("Reply deleted with SEQ: {}", replySeq);

        String message = "Reply deleted successfully";
        log.info(message);
        return message;
    }
}
