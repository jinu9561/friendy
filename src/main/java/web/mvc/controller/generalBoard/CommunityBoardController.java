package web.mvc.controller.generalBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.generalBoard.CommunityBoardDTO;
import web.mvc.service.generalBoard.CommunityBoardService;

import java.util.List;

@RestController
@RequestMapping("/community-board")
public class CommunityBoardController {

    @Autowired
    private CommunityBoardService communityBoardService;

    @PostMapping
    public ResponseEntity<CommunityBoardDTO> createCommunityBoard(@RequestBody CommunityBoardDTO communityBoardDTO) {
        return ResponseEntity.ok(communityBoardService.createCommunityBoard(communityBoardDTO));
    }

    @GetMapping("/{commBoardSeq}")
    public ResponseEntity<CommunityBoardDTO> getCommunityBoardById(@PathVariable Long commBoardSeq) {
        return ResponseEntity.ok(communityBoardService.getCommunityBoardById(commBoardSeq));
    }

    @GetMapping
    public ResponseEntity<List<CommunityBoardDTO>> getAllCommunityBoards() {
        return ResponseEntity.ok(communityBoardService.getAllCommunityBoards());
    }

    @PutMapping("/{commBoardSeq}")
    public ResponseEntity<CommunityBoardDTO> updateCommunityBoard(@PathVariable Long commBoardSeq, @RequestBody CommunityBoardDTO communityBoardDTO) {
        return ResponseEntity.ok(communityBoardService.updateCommunityBoard(commBoardSeq, communityBoardDTO));
    }

    @DeleteMapping("/{commBoardSeq}")
    public ResponseEntity<Void> deleteCommunityBoard(@PathVariable Long commBoardSeq) {
        communityBoardService.deleteCommunityBoard(commBoardSeq);
        return ResponseEntity.noContent().build();
    }
}
