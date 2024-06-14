package web.mvc.service.generalBoard;

import web.mvc.dto.generalBoard.ReplyDTO;

import java.util.List;

public interface ReplyService {

    ReplyDTO createReply(ReplyDTO replyDTO);

    ReplyDTO getReplyById(Long replySeq);

    List<ReplyDTO> getRepliesByBoardSeq(Long boardSeq);

    ReplyDTO updateReply(ReplyDTO replyDTO);

    String deleteReply(Long replySeq);
}