package web.mvc.service.generalBoard;

import web.mvc.dto.generalBoard.ReplyDTO;

import web.mvc.entity.generalBoard.Reply;

import java.util.List;

public interface ReplyService {
    List<Reply> getRepliesByCommBoardSeq(Long commBoardSeq);
    ReplyDTO addReply(ReplyDTO replyDTO);
    String deleteReply(Long replySeq);

}