package web.mvc.controller.generalBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.generalBoard.ReplyDTO;
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
    public ResponseEntity<List<ReplyDTO>> getRepliesByBoardSeq(@PathVariable Long boardSeq){
        return ResponseEntity.ok(replyService.getRepliesByBoardSeq(boardSeq));
    }

    //댓글 생성
    @PostMapping("/")
    public ResponseEntity<ReplyDTO> createReply(@RequestBody ReplyDTO replyDTO){
        return ResponseEntity.ok(replyService.createReply(replyDTO));
    }

    //특정 id(replySeq)를 가진 댓글 조회
    @GetMapping("/{replySeq}")
    public ResponseEntity<ReplyDTO> getReplyById(@PathVariable Long replySeq){
        return ResponseEntity.ok(replyService.getReplyById(replySeq));
    }

    //특정 id(replySeq)를 가진 댓글 수정
    @PutMapping("/{replySeq}")
    public ResponseEntity<ReplyDTO> updateReply(@PathVariable Long replySeq, @RequestBody ReplyDTO replyDTO){
        replyDTO.setReplySeq(replySeq);
        return ResponseEntity.ok(replyService.updateReply(replyDTO));
    }

    //특정 id(replySeq)를 가진 댓글 삭제
    @DeleteMapping("/{replySeq}")
    public String deleteReply(@PathVariable Long replySeq){
        return replyService.deleteReply(replySeq);
    }
}
