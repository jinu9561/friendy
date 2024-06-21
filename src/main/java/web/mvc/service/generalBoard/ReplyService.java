package web.mvc.service.generalBoard;

import web.mvc.dto.generalBoard.ReplyReqDTO;
import web.mvc.dto.generalBoard.ReplyResDTO;
import web.mvc.entity.generalBoard.Reply;

import java.util.List;

public interface ReplyService {

    ReplyResDTO addReply(Long boardSeq, ReplyReqDTO replyReqDTO);

    public Reply getReplyById(Long replySeq);

    public List<Reply> getRepliesByCommBoardId(Long commBoardSeq);

    ReplyResDTO updateReply(Long replyId, ReplyReqDTO replyReqDTO);

    public String deleteReply(Long replySeq);


}