package web.mvc.controller.generalBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.generalBoard.CommunityBoardDTO;
import web.mvc.service.generalBoard.CommunityBoardService;

import java.util.List;

@RequiredArgsConstructor //@RequiredArgsConstructor는 Lombok 라이브러리에서 제공하는 어노테이션 중 하나로, final 또는 @NonNull 필드를 초기화하는 생성자를 자동으로 생성
@RestController
@RequestMapping("/community-boards")
@Slf4j
public class CommunityBoardController {

    @Autowired
    private CommunityBoardService communityBoardService;

    @GetMapping
    public ResponseEntity<List<CommunityBoardDTO>> getAllCommunityBoards() {
        return ResponseEntity.ok(communityBoardService.getAllCommunityBoards());
    }

    @GetMapping("/{commBoardSeq}")
    public ResponseEntity<CommunityBoardDTO> getCommunityBoardById(@PathVariable Long commBoardSeq) {
        return ResponseEntity.ok(communityBoardService.getCommunityBoardById(commBoardSeq)); //HTTP 상태 코드 200을 사용하여 요청이 성공적으로 처리되었음을 클라이언트에 알림)
    }


    @PostMapping("/board")
    public ResponseEntity<CommunityBoardDTO> createCommunityBoard(@RequestBody CommunityBoardDTO communityBoardDTO) {
        return ResponseEntity.ok(communityBoardService.createCommunityBoard(communityBoardDTO));
    }

    @PutMapping("/{commBoardSeq}")
    public ResponseEntity<CommunityBoardDTO> updateCommunityBoard(@PathVariable Long commBoardSeq, @RequestBody CommunityBoardDTO communityBoardDTO) {
        return ResponseEntity.ok(communityBoardService.updateCommunityBoard(commBoardSeq, communityBoardDTO));
    }

    /*특정 ID(commBoardSeq)를 가진 커뮤니티 보드를 삭제하고, 성공적으로 삭제되었음을 알리기 위해 HTTP 상태 코드 204(No Content)를 반환. 이 경우 응답 본문은 없기 때문에 Void 타입을 사용*/
    @DeleteMapping("/{commBoardSeq}")
    public ResponseEntity<Void> deleteCommunityBoard(@PathVariable Long commBoardSeq) {
        communityBoardService.deleteCommunityBoard(commBoardSeq);
        return ResponseEntity.noContent().build();
    }
}
