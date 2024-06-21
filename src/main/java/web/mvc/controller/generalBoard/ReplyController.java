package web.mvc.controller.generalBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.generalBoard.ReplyReqDTO;
import web.mvc.service.generalBoard.ReplyService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor

public class ReplyController {
    private final ReplyService replyService;

    // 게시물에 대한 모든 댓글 조회
    @GetMapping("/board/{boardSeq}")
    public ResponseEntity<List<ReplyReqDTO>> getRepliesByBoardSeq(@PathVariable Long boardSeq){
        return ResponseEntity.ok(replyService.getRepliesByBoardSeq(boardSeq));
    }

    //댓글 생성
    @PostMapping("/")
    public ResponseEntity<ReplyReqDTO> createReply(@RequestBody ReplyReqDTO replyReqDTO){
        return ResponseEntity.ok(replyService.createReply(replyReqDTO));
    }

    //특정 id(replySeq)를 가진 댓글 조회
    @GetMapping("/{replySeq}")
    public ResponseEntity<ReplyReqDTO> getReplyById(@PathVariable Long replySeq){
        return ResponseEntity.ok(replyService.getReplyById(replySeq));
    }

    //특정 id(replySeq)를 가진 댓글 수정
    @PutMapping("/{replySeq}")
    public ResponseEntity<ReplyReqDTO> updateReply(@PathVariable Long replySeq, @RequestBody ReplyReqDTO replyReqDTO){
        replyReqDTO.setReplySeq(replySeq);
        return ResponseEntity.ok(replyService.updateReply(replyReqDTO));
    }

    //특정 id(replySeq)를 가진 댓글 삭제
    @DeleteMapping("/{replySeq}")
    public String deleteReply(@PathVariable Long replySeq){
        return replyService.deleteReply(replySeq);
    }
}
