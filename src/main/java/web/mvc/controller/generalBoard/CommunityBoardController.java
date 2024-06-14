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

    /*모든 커뮤니티 게시물을 조회*/
    @GetMapping("/")
    public ResponseEntity<List<CommunityBoardDTO>> getAllCommunityBoards() {
        return ResponseEntity.ok(communityBoardService.getAllCommunityBoards());
    }

    /*커뮤니티 게시물을 생성*/
    @PostMapping("/")
    public ResponseEntity<CommunityBoardDTO> createCommunityBoard(@RequestBody CommunityBoardDTO communityBoardDTO) {
        return ResponseEntity.ok(communityBoardService.createCommunityBoard(communityBoardDTO));
    }

    /*특정 ID(commBoardSeq)를 가진 커뮤니티 게시물을 조회*/
    @GetMapping("/{commBoardSeq}")
    public ResponseEntity<CommunityBoardDTO> getCommunityBoardById(@PathVariable Long commBoardSeq) {
        return ResponseEntity.ok(communityBoardService.getCommunityBoardById(commBoardSeq)); //HTTP 상태 코드 200을 사용하여 요청이 성공적으로 처리되었음을 클라이언트에 알림)
    }

    /*특정 ID(commBoardSeq)를 가진 커뮤니티 게시물을 수정*/
    @PutMapping("/{commBoardSeq}")
    public ResponseEntity<CommunityBoardDTO> updateCommunityBoard(@PathVariable Long commBoardSeq, @RequestBody CommunityBoardDTO communityBoardDTO) {
        communityBoardDTO.setCommBoardSeq(commBoardSeq); // URL 경로에서 받은 ID를 DTO에 설정
        return ResponseEntity.ok(communityBoardService.updateCommunityBoard(communityBoardDTO));
    }

    /*특정 ID(commBoardSeq)를 가진 커뮤니티 게시물을 삭제*/
    @DeleteMapping("/{commBoardSeq}")
    public String deleteCommunityBoard(@PathVariable Long commBoardSeq) {
        return communityBoardService.deleteCommunityBoard(commBoardSeq);
    }
}
